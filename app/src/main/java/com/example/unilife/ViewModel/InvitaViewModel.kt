package com.example.unilife.ViewModel


import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unilife.Model.Gruppo
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.ImpostazioniDB
import com.example.unilife.Repository.UtenteRepo


class InvitaViewModel: ViewModel() {
    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }


    private val gruppoRepo = GruppoRepo()
    private val utenteRepo = UtenteRepo()

    private val _emailIntent = MutableLiveData<Intent>()
    val emailIntent: LiveData<Intent> get() = _emailIntent


    private var _partecipanti = MutableLiveData<ArrayList<String>>()
    val partecipanti: LiveData<ArrayList<String>> get() = _partecipanti

    private var _idGruppo = MutableLiveData<String?>()
    val idGruppo: LiveData<String?> get() = _idGruppo


    init {
        getIdGruppoUtente()
    }

    fun loadData()
    {
        //inizializzazione partecipanti prendendo i dati dal repo
        if (_idGruppo.value != null) {
            gruppoRepo.getGruppo(_idGruppo.value!!).addSnapshotListener { gruppo, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if(gruppo?.toObject(Gruppo::class.java)?.partecipanti != null) {
                    _partecipanti.value =
                        gruppo.toObject(Gruppo::class.java)!!.partecipanti as ArrayList<String>
                }
            }
        }
    }

    fun creaGruppo() {
        Log.d("crea gruppo inf", "viewmodel")
        utenteRepo.getUtente().addOnSuccessListener { utente ->
            val username = utente!!.toObject(Utente::class.java)!!.username.toString()
            Log.d("crea gruppo inf", "username ${username}")
            gruppoRepo.creaGruppo(username).addOnFailureListener {
                Log.d("CreaGruppo", "gruppo non creato")
            }
                .addOnSuccessListener { gruppoDoc ->
                    //gruppo appena creato, prendo il suo id
                    val idGruppo = gruppoDoc.id
                    utenteRepo.setIdGruppo(idGruppo).addOnFailureListener { e ->
                        Log.d("CreaGruppo", "idGruppo utente non settato ${e}")
                    }
                }
        }
    }

    fun getIdGruppoUtente()
    {
        utenteRepo.getUtente().addOnSuccessListener { utente ->
            _idGruppo.value = utente?.toObject(Utente::class.java)?.id_gruppo
            loadData()
        }
    }


    /**rimuovere il partecipante dal gruppo e pulire il campo "id_gruppo" dell'utente
    controlla se ci sono partecipanti, se la lista è vuota allora viene eliminato il gruppo**/
    fun rimuoviPartecipante(posizione: Int)
    {
        Log.d("Rimozione partecipanti", "user${_partecipanti.value!!.get(posizione)}")

        val username = _partecipanti.value!!.get(posizione)
        gruppoRepo.rimuoviPartecipante(username,_idGruppo.value!!).addOnFailureListener{
            Log.d("Rimozione partecipanti", "errore nella rimozione del partecipante")
        }
        _partecipanti.value!!.remove(username)


        Log.d("Rimozione partecipanti", "username ${username}")
        utenteRepo.getIdUtenteDaUsername(username).addOnSuccessListener { utente ->
            Log.d("Rimozione partecipanti", "utente, ${utente}")
            //se troviamo un documento con l'username corrispondente, otteniamo l'ID dell'utente
            val document = utente.documents[0] // Otteniamo il primo documento (nel caso ce ne sia più di uno)
            Log.d("Rimozione partecipanti", "docuemnti, ${document}")
            val idUtente = document.id // Otteniamo l'ID del documento
            Log.d("Rimozione partecipanti", "id utente, ${idUtente}")
            utenteRepo.setIdGruppoByIdUtente(idUtente)
        }
            .addOnFailureListener{e->
                Log.d("Rimozione partecipanti", "fail ${e}")

            }

        if (_partecipanti.value!!.isEmpty())
        {
            Log.d("Rimozione partecipanti", "partecipanti vuota, ${_idGruppo}")
            gruppoRepo.eliminaGruppo(_idGruppo.value!!).addOnFailureListener{
                Log.d("Rimozione partecipanti", "eliminazione gruppo fallita")
            }
        }
    }

    //se idgruppo non è nullo allora invita e basta, altrimenti crea il gruppo
    fun invita(destinatario:String)
    {
        if(_idGruppo.value != null)
        {
            val soggetto = "Invito al gruppo di coinquilini"
            val corpo =
                "Sei stato invitato al gruppo di coinquilini, registrati all'app se non l'hai" +
                        " ancora" + " fatto e inserisci il codice: ${_idGruppo.value}"
            val mIntent = Intent(Intent.ACTION_SEND).apply {
                /*To send an email you need to specify mailto: as URI using setData() method
            and data type will be to text/plain using setType() method*/
                data = Uri.parse("mailto:")
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(destinatario))
                putExtra(Intent.EXTRA_SUBJECT, soggetto)
                putExtra(Intent.EXTRA_TEXT, corpo)
            }
            _emailIntent.value = mIntent
        }
    }

}
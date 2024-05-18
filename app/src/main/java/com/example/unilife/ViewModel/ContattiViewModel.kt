package com.example.unilife.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unilife.Model.Gruppo
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.UtenteRepo

class ContattiViewModel: ViewModel() {

    private val gruppoRepo = GruppoRepo()
    private val utenteRepo = UtenteRepo()

    private var _contatti = MutableLiveData<MutableMap<String, String>>()
    val contatti: LiveData<MutableMap<String, String>> get() = _contatti
    private var idGruppo : String? = null

    init {
        getIdGruppoUtente()
    }

    fun loadData() {
        //inizializzazione listaSpesa prendendo i dati dal repo
        if (idGruppo != null) {
            gruppoRepo.getGruppo(idGruppo!!).addSnapshotListener { gruppo, e ->
                if (e != null) {
                    // Gestisci l'eccezione se si verifica un errore
                    return@addSnapshotListener
                }
                if (gruppo?.toObject(Gruppo::class.java)?.listaSpesa != null) {
                    _contatti.value =
                        gruppo.toObject(Gruppo::class.java)?.contatti as MutableMap<String, String>
                }
            }
        }
    }

    fun getIdGruppoUtente()
    {
        utenteRepo.getUtente().addOnSuccessListener { utente ->
            idGruppo = utente.toObject(Utente::class.java)?.id_gruppo
            Log.d("inizializza", "idGruppo ${idGruppo}")
            loadData()
        }
    }

    fun aggiungiContatto(nomeContatto: String, numeroTel:String) {
        if (idGruppo != null) {
            _contatti.value?.put(nomeContatto, numeroTel )
            gruppoRepo.aggiungiContatto(nomeContatto, numeroTel, idGruppo!!).addOnFailureListener {
                Log.e("contatti","Failed adding element")
            }
        }
    }

    fun rimuoviContatto(chiave: String) {
        if (idGruppo != null) {
            if (chiave != null) {
                gruppoRepo.rimuoviContatto(chiave, idGruppo!!).addOnFailureListener {
                    Log.e("contatti","Failed to delete element")
                }
                _contatti.value?.remove(chiave)
            }
        }
    }

}
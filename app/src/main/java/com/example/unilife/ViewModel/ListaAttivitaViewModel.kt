package com.example.unilife.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unilife.Model.Attivita
import com.example.unilife.Model.Gruppo
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.UtenteRepo
import org.checkerframework.checker.units.qual.A

class ListaAttivitaViewModel:ViewModel() {

    private val gruppoRepo = GruppoRepo()
    private val utenteRepo = UtenteRepo()

    private var _listaAttivita = MutableLiveData<ArrayList<String>>()
    val listaAttivita: LiveData<ArrayList<String>> get() = _listaAttivita

    private var idGruppo: String? = null

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
                    val attivita = gruppo.toObject(Gruppo::class.java)?.attivita

                    _listaAttivita.value = attivita?.map { it.titolo }?.let { ArrayList(it) }

                }
            }
        }
    }

    fun getIdGruppoUtente() {
        utenteRepo.getUtente().addOnSuccessListener { utente ->
            idGruppo = utente.toObject(Utente::class.java)?.id_gruppo
            Log.d("inizializza", "idGruppo ${idGruppo}")
            loadData()
        }
    }

    fun getAttivitaByData(data:String)
    {
        if(idGruppo != null)
        {
        gruppoRepo.getAttivitaByData(data, idGruppo!!).addOnSuccessListener { listaAttivita ->
            _listaAttivita.value = listaAttivita as ArrayList<String>
            }
        }
    }
}

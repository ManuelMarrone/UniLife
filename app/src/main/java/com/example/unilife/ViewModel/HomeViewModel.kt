package com.example.unilife.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unilife.Model.Gruppo
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.UtenteRepo

/**
 *  La responsabilità principale di ViewModel è caricare e gestire i dati relativi all'interfaccia utente,
 *  il che lo rende un ottimo candidato per l'archiviazione
 *  di oggetti LiveData. Crea LiveData oggetti in ViewModel e utilizzali per esporre lo stato al livello Ui
 **/
class HomeViewModel:ViewModel() {
    private val gruppoRepo = GruppoRepo()
    private val utenteRepo = UtenteRepo()

    private var _listaSpesa = MutableLiveData<ArrayList<String>>()
    val listaSpesa: LiveData<ArrayList<String>> get() = _listaSpesa
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
                    _listaSpesa.value =
                        gruppo.toObject(Gruppo::class.java)?.listaSpesa as ArrayList<String>
                }
            }
        }
    }

    fun getIdGruppoUtente()
    {
        val utenteTask = utenteRepo.getUtente()
        if (utenteTask != null) {
            utenteTask.addOnSuccessListener { utente ->
                val utenteObj = utente?.toObject(Utente::class.java)
                if (utenteObj != null) {
                    idGruppo = utenteObj.id_gruppo
                    Log.d("inizializza", "idGruppo ${idGruppo}")
                    loadData()
                } else {
                    Log.e("HomeViewModel", "Utente è null")
                }
            }.addOnFailureListener {
                Log.e("HomeViewModel", "Errore nel recuperare l'utente", it)
            }
        } else {
            Log.e("HomeViewModel", "Errore: utente non autenticato")
        }
    }


    fun aggiungiElementoListaSpesa(nome: String) {
        if (idGruppo != null) {
            _listaSpesa.value?.add(nome)
            gruppoRepo.aggiungiElementoListaSpesa(nome, idGruppo!!).addOnFailureListener {
                Log.e("ListaSpesa","Failed adding element")
            }
        }
    }

    fun rimuoviElemento(position: Int) {
            if (idGruppo != null) {
                val nome = _listaSpesa.value?.get(position)
                if (nome != null) {
                    gruppoRepo.rimuoviElementoListaSpesa(nome, idGruppo!!).addOnFailureListener {
                        Log.e("ListaSpesa","Failed to delete element")
                    }
                    _listaSpesa.value?.removeAt(position)
                }
            }
    }
}
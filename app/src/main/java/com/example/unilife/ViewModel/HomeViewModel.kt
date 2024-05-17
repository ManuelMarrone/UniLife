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

    fun loadData()
    {
        //inizializzazione listaSpesa prendendo i dati dal repo
        if (idGruppo != null) {
            gruppoRepo.getGruppo(idGruppo!!).addSnapshotListener { gruppo, e ->
                if (e != null) {
                    // Gestisci l'eccezione se si verifica un errore
                    return@addSnapshotListener
                }
                _listaSpesa.value = gruppo?.toObject(Gruppo::class.java)?.listaSpesa as ArrayList<String>
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
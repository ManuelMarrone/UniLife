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
import java.time.LocalDate
import java.util.Date

class AggiungiAttivitaViewModel:ViewModel() {

    private val gruppoRepo = GruppoRepo()
    private val utenteRepo = UtenteRepo()

    private var _partecipanti = MutableLiveData<MutableMap<String,Boolean>>()
    val partecipanti: LiveData<MutableMap<String,Boolean>> get() = _partecipanti

    private var _isValid = MutableLiveData<Boolean>()
    val isValid: LiveData<Boolean> get() = _isValid

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
                    val partecipantiGruppo = gruppo.toObject(Gruppo::class.java)?.partecipanti as ArrayList<String>

                    val newMap = partecipantiGruppo.associateWith { false } as MutableMap<String,Boolean>

                    // Aggiornare il valore di _partecipanti
                    _partecipanti.value = newMap

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

    fun setChecked(username: String)
    {
        val valore = !(_partecipanti.value?.get(username))!!
        // Imposta nuovamente la mappa modificata all'interno di _partecipanti
        _partecipanti.value?.set(username, valore)
    }

    fun validaInput()
    {
        _isValid.value = _partecipanti.value!!.values.any { it == true }
    }

    fun aggiungiAttivita(titolo: String, data: String) {
        if (idGruppo != null) {
            val attivita = Attivita(titolo, data, _partecipanti.value!!)
            gruppoRepo.aggiungiAttivita(attivita, idGruppo!!).addOnFailureListener {e->
                Log.e("attivita","Failed adding element ${e}")
            }

        }
    }



}
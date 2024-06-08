package com.example.unilife.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unilife.Model.Attivita
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.UtenteRepo

class VisualizzaModificaAttivitaViewModel:ViewModel() {
    private val gruppoRepo = GruppoRepo()
    private val utenteRepo = UtenteRepo()


    private var _partecipanti = MutableLiveData<MutableMap<String,Boolean>>()
    val partecipanti: LiveData<MutableMap<String,Boolean>> get() = _partecipanti

    private var _isPartecipante = MutableLiveData<Boolean>()
    val isPartecipante : LiveData<Boolean> get() = _isPartecipante

    private var _isValid = MutableLiveData<Boolean>()
    val isValid: LiveData<Boolean> get() = _isValid

    private var idGruppo : String? = null

    init {
        getIdGruppoUtente()
    }

    fun getIdGruppoUtente()
    {
        utenteRepo.getUtente()?.addOnSuccessListener { utente ->
            idGruppo = utente.toObject(Utente::class.java)?.id_gruppo
        }
    }

    fun setPartecipantiAttività(partecipantiAttivita:Map<String, Boolean>)
    {
        val newMap = partecipantiAttivita as MutableMap<String,Boolean>

        // Aggiornare il valore di _partecipanti
        _partecipanti.value = newMap
    }


    //al click di completa mette a false il value dell'utente che l'ha cliccata
    //se tutti sono a false allora elimina l'attività automaticamente
    fun completaAttivita(idAttivita:String, partecipantiAttivita:MutableMap<String, Boolean>)
    {
            if (idGruppo != null) {
                utenteRepo.getUtente()?.addOnSuccessListener { utente ->
                    val usernameUtente = utente.toObject(Utente::class.java)?.username
                    partecipantiAttivita[usernameUtente.toString()] = false
                    gruppoRepo.rimuoviPartecipanteAttivita(
                        idAttivita,
                        idGruppo!!,
                        partecipantiAttivita
                    ).addOnFailureListener{e->
                        Log.d("attivitaPart" , "failed ${e}")
                    }
                    if (partecipantiAttivita.values.all { it == false })
                    {
                        gruppoRepo.rimuoviAttivita(idAttivita, idGruppo!!)
                    }
                }

            }
    }

    fun isPartecipante (partecipantiAttivita:Map<String, Boolean>)
    {
        utenteRepo.getUtente()?.addOnSuccessListener { utente ->
            val usernameUtente = utente.toObject(Utente::class.java)?.username
            if (partecipantiAttivita.containsKey(usernameUtente)) {
                //ottieni il valore booleano associato alla chiave
                val stato = partecipantiAttivita[usernameUtente]
                //verifica se il valore booleano è true
                _isPartecipante.value = stato == true
            }
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

    fun salvaModifica(idAttivita: String, titolo: String, data: String) {
            if (idGruppo != null) {
                    val attivita = Attivita(titolo, data, _partecipanti.value!!)
                    gruppoRepo.modificaAttivita(idAttivita,attivita, idGruppo!!).addOnFailureListener { e ->
                        Log.e("attivita", "Failed adding element ${e}")
                    }

                }

    }
}

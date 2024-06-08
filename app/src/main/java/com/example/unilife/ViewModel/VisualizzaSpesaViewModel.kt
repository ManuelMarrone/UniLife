package com.example.unilife.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.UtenteRepo

class VisualizzaSpesaViewModel:ViewModel() {
    private val gruppoRepo = GruppoRepo()
    private val utenteRepo = UtenteRepo()


    private var _partecipanti = MutableLiveData<MutableMap<String,Boolean>>()
    val partecipanti: LiveData<MutableMap<String, Boolean>> get() = _partecipanti

    private var _isPartecipante = MutableLiveData<Boolean>()
    val isPartecipante : LiveData<Boolean> get() = _isPartecipante


    private var idGruppo : String? = null

    init {
        getIdGruppoUtente()
    }
    fun getIdGruppoUtente()
    {
<<<<<<< HEAD
        utenteRepo.getUtente()?.addOnSuccessListener { utente ->
=======
        utenteRepo.getUtente().addOnSuccessListener { utente ->
>>>>>>> e3e61815e5443287f29611ab6c96961b462470ec
            idGruppo = utente.toObject(Utente::class.java)?.id_gruppo
        }
    }

    fun setPartecipantiPagamento(partecipantiPagamento:Map<String, Boolean>)
    {
        val newMap = partecipantiPagamento as MutableMap<String,Boolean>

        // Aggiornare il valore di _partecipanti
        _partecipanti.value = newMap
    }

    fun completaPagamento(idPagamento:String, partecipantiPagamento:MutableMap<String, Boolean>)
    {
        if (idGruppo != null) {
<<<<<<< HEAD
            utenteRepo.getUtente()?.addOnSuccessListener { utente ->
=======
            utenteRepo.getUtente().addOnSuccessListener { utente ->
>>>>>>> e3e61815e5443287f29611ab6c96961b462470ec
                val usernameUtente = utente.toObject(Utente::class.java)?.username
                partecipantiPagamento[usernameUtente.toString()] = false
                gruppoRepo.rimuoviPartecipantePagamento(
                    idPagamento,
                    idGruppo!!,
                    partecipantiPagamento
                ).addOnFailureListener{e->
                    Log.d("attivitaPart" , "failed ${e}")
                }
                if (partecipantiPagamento.values.all { it == false })
                {
                    gruppoRepo.rimuoviPagamento(idPagamento, idGruppo!!)
                }
            }

        }
    }

    fun isPartecipante (partecipantiPagamento:Map<String, Boolean>)
    {
<<<<<<< HEAD
        utenteRepo.getUtente()?.addOnSuccessListener { utente ->
=======
        utenteRepo.getUtente().addOnSuccessListener { utente ->
>>>>>>> e3e61815e5443287f29611ab6c96961b462470ec
            val usernameUtente = utente.toObject(Utente::class.java)?.username
            if (partecipantiPagamento.containsKey(usernameUtente)) {
                //ottieni il valore booleano associato alla chiave
                val stato = partecipantiPagamento[usernameUtente]
                //verifica se il valore booleano è true
                _isPartecipante.value = stato == true
            }
        }
    }
}
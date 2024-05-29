package com.example.unilife.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.UtenteRepo

class HomeNoGruppiViewModel: ViewModel() {

    private val gruppoRepo = GruppoRepo()
    private val utenteRepo = UtenteRepo()

    private var _isValid = MutableLiveData<Boolean>()
    val isValid: LiveData<Boolean> get() = _isValid

    //funzione che preleva tutti i gruppi salvati nel database e controlla se c'è un id che corrisponde
    fun validaCodice(code:String) {
        gruppoRepo.getGruppo(code).addSnapshotListener { gruppo, e->
            if (gruppo != null) {
                if (gruppo.exists()) {
                    _isValid.value = true
                }
                else
                {
                    _isValid.value = false
                }
            }
            else
            {
                _isValid.value = false
            }
        }
    }

    fun aggiungiUtenteGruppo(idGruppo:String)
    {
        utenteRepo.setIdGruppo(idGruppo).addOnFailureListener{
            Log.d("AggiuntiUtenteGruppo", "idGruppo utente non settato")
        }
        utenteRepo.getUtente().addOnSuccessListener { utente ->
            val username = utente!!.toObject(Utente::class.java)!!.username
            gruppoRepo.aggiungiPartecipante(username!!, idGruppo).addOnFailureListener{
                Log.d("AggiuntiUtenteGruppo", "username non salvato in lista")
            }
        }
    }

}
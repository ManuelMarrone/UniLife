package com.example.unilife.ViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.ImpostazioniDB
import com.example.unilife.Repository.UtenteRepo
import kotlinx.coroutines.launch

class HomeNoGruppiViewModel: ViewModel() {
    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }

    private val repository = GruppoRepo()
    private val utenteRepo = UtenteRepo()

    //funzione che preleva tutti i gruppi salvati nel database e controlla se c'è un id che corrisponde
//    fun validaCodice(code:String, callback: (Boolean?) -> Unit) {
//        repository.controllaCodiceInvito(code, callback)
//    }
    fun validaCodice(code: String, callback: (Boolean?) -> Unit) {
        viewModelScope.launch {
            repository.controllaCodiceInvito(code, callback)
        }
    }

    fun aggiungiUtenteGruppo(idGruppo:String)
    {
        utenteRepo.setIdGruppo(idGruppo)
        repository.aggiungiPartecipante(idGruppo)
    }

}
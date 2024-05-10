package com.example.unilife.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.ImpostazioniDB
import kotlinx.coroutines.launch

class HomeNoGruppiViewModel: ViewModel() {
    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }

    private val repository = GruppoRepo()

    //funzione che preleva tutti i gruppi salvati nel database e controlla se c'è un id che corrisponde
//    fun validaCodice(code:String, callback: (Boolean?) -> Unit) {
//        repository.controllaCodiceInvito(code, callback)
//    }
    fun validaCodice(code: String, callback: (Boolean?) -> Unit) {
        viewModelScope.launch {
            repository.controllaCodiceInvito(code, callback)
        }
    }
}
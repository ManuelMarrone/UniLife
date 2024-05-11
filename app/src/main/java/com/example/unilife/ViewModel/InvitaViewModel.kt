package com.example.unilife.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.ImpostazioniDB
import com.example.unilife.Repository.UtenteRepo
import kotlinx.coroutines.launch



class InvitaViewModel: ViewModel() {
    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }


    private val repository = GruppoRepo()
    private val utenteRepo = UtenteRepo()

    fun creaGruppo(callback: (String?) -> Unit) {
        viewModelScope.launch {
            repository.creaGruppo()
            utenteRepo.getIdGruppo(callback)
        }
    }

    fun getIdGruppo(callback: (String?) -> Unit) {
        utenteRepo.getIdGruppo(callback)
    }

    //metodo che preleva i partecipanti del gruppo se esiste dell'utente loggato
    fun getPartecipantiGruppo(callback: (ArrayList<String>?) -> Unit){
        //chiamata al repository per prendere l'informazione
        viewModelScope.launch {
            utenteRepo.getUsernamePartecipanti(callback)
        }
    }

}
package com.example.unilife.ViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.ImpostazioniDB
import com.example.unilife.Repository.UtenteRepo
import kotlinx.coroutines.launch



class InvitaViewModel: ViewModel() {
    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }


    private val gruppoRepo = GruppoRepo()
    private val utenteRepo = UtenteRepo()



    fun creaGruppo(callback: (String?) -> Unit) {
        viewModelScope.launch {
            gruppoRepo.creaGruppo()
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

    //rimuovere il partecipante dal gruppo
    //e pulire il campo "id_gruppo" dell'utente
    fun rimuoviPartecipante(username: String)
    {
        viewModelScope.launch {
            gruppoRepo.rimuoviPartecipante(username)
            utenteRepo.setIdGruppoDaUsername(username)
        }
    }

}
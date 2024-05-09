package com.example.unilife.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.ImpostazioniDB
import com.example.unilife.Repository.UtenteRepo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch



class InvitaViewModel: ViewModel() {
    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }


    private val repository = GruppoRepo()
    private val utenteRepo = UtenteRepo()

    // LiveData per contenere il valore dell'ID del gruppo
    private val idGruppoLiveData = MutableLiveData<String?>()



//    private val _uiState = MutableStateFlow(StatoRegistrazioneUi())
//    val uiState: StateFlow<StatoRegistrazioneUi> = _uiState.asStateFlow()

    fun creaGruppo(callback: (String?) -> Unit) {
        viewModelScope.launch {
            repository.creaGruppo()
            utenteRepo.getIdGruppo(callback)
        }
    }

    // Funzione per ottenere il gruppo
    fun getGruppo() {
        idGruppoLiveData.postValue(null) // Resetta il valore mentre attendi i nuovi dati
        utenteRepo.getGruppo().observeForever { idGruppo ->
            idGruppoLiveData.postValue(idGruppo)
        }
    }

    fun getIdGruppo(callback: (String?) -> Unit) {
        utenteRepo.getIdGruppo(callback)
    }

    // Funzione per ottenere l'ID del gruppo come LiveData
//    fun getIdGruppoLiveData(): LiveData<String?> {
//        return idGruppoLiveData
//    }
//
//    fun getIdGruppoLiveData(): LiveData<String?> {
//        return utenteRepo.getIdGruppo()
//    }

}
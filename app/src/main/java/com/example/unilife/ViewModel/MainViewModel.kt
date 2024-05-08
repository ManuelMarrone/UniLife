package com.example.unilife.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unilife.Repository.UtenteRepo

class MainViewModel : ViewModel(){
    private val utenteRepo = UtenteRepo()

    // LiveData per contenere il valore dell'ID del gruppo
    private val idGruppoLiveData = MutableLiveData<String?>()

    // Funzione per ottenere il gruppo
    fun getGruppo() {
        idGruppoLiveData.postValue(null) // Resetta il valore mentre attendi i nuovi dati
        utenteRepo.getGruppo().observeForever { idGruppo ->
            idGruppoLiveData.postValue(idGruppo)
        }
    }

    // Funzione per ottenere l'ID del gruppo come LiveData
    fun getIdGruppoLiveData(): LiveData<String?> {
        return idGruppoLiveData
    }



}
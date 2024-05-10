package com.example.unilife.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unilife.Repository.UtenteRepo

class MainViewModel : ViewModel(){
    private val utenteRepo = UtenteRepo()

    // LiveData per contenere il valore dell'ID del gruppo
    private val _idGruppoLiveData = MutableLiveData<String?>()
    val idGruppoLiveData: LiveData<String?>
    get() = _idGruppoLiveData

    // Funzione per ottenere il gruppo
    fun getGruppo() {
        _idGruppoLiveData.postValue(null) // Resetta il valore mentre attendi i nuovi dati
        utenteRepo.getGruppo().observeForever { idGruppo ->
            _idGruppoLiveData.postValue(idGruppo)
        }
    }




}
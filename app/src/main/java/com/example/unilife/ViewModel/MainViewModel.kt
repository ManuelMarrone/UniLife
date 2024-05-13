package com.example.unilife.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.UtenteRepo

class MainViewModel : ViewModel(){
    private val utenteRepo = UtenteRepo()

    // LiveData per contenere il valore dell'ID del gruppo
    private val _idGruppoLiveData = MutableLiveData<String?>()
    val idGruppoLiveData: LiveData<String?>
    get() = _idGruppoLiveData

    private val _utenteLiveData = MutableLiveData<Utente?>()
    val utenteLiveData : LiveData<Utente?> = _utenteLiveData

    val updateTrigger: MutableLiveData<Unit?> = MutableLiveData()

    init{
        utenteRepo.startListening { utente ->
            _utenteLiveData.value = utente

            //controlla il cambiamento di id_gruppo
            utente?.id_gruppo?.let {
                idGruppo->
                if(idGruppo == null)
                {
                    updateTrigger.postValue(null)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        utenteRepo.stopListening()
    }

    // Funzione per ottenere il gruppo
    fun getGruppo() {
        _idGruppoLiveData.postValue(null) // Resetta il valore mentre attendi i nuovi dati
        utenteRepo.getGruppo().observeForever { idGruppo ->
            _idGruppoLiveData.postValue(idGruppo)
        }
    }




}
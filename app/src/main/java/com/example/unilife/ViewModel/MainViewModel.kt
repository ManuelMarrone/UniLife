package com.example.unilife.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.UtenteRepo

class MainViewModel : ViewModel(){
    private val utenteRepo = UtenteRepo()

    private var _idGruppoUtente= MutableLiveData<String?>()
    val idGruppoUtente: LiveData<String?> get() = _idGruppoUtente

    init{
        getIdGruppoUtente()
    }

    fun getIdGruppoUtente()
    {
        utenteRepo.getUtenteLive()?.addSnapshotListener { utente,e ->
            _idGruppoUtente.value = utente?.toObject(Utente::class.java)?.id_gruppo
        }
    }

}
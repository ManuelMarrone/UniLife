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
<<<<<<< HEAD
        utenteRepo.getUtenteLive()?.addSnapshotListener { utente,e ->
=======
        utenteRepo.getUtenteLive().addSnapshotListener { utente,e ->
>>>>>>> e3e61815e5443287f29611ab6c96961b462470ec
            _idGruppoUtente.value = utente?.toObject(Utente::class.java)?.id_gruppo
        }
    }

}
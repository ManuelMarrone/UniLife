package com.example.unilife.ViewModel

import android.util.Log
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
        utenteRepo.getUtente().addOnSuccessListener { utente ->
            if(utente != null ) {
                _idGruppoUtente.value = utente.toObject(Utente::class.java)?.id_gruppo
                Log.d("login" , "id ${_idGruppoUtente}")
            }
=======
        utenteRepo.getUtenteLive().addSnapshotListener { utente,e ->
            _idGruppoUtente.value = utente?.toObject(Utente::class.java)?.id_gruppo
>>>>>>> parent of 3e92c37 (gestione account)
        }
    }

}
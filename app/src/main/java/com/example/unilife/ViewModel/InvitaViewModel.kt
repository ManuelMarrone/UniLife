package com.example.unilife.ViewModel

import android.R.attr
import android.app.blob.BlobStoreManager
import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.ImpostazioniDB
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch



class InvitaViewModel: ViewModel() {
    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }


    private val repository = GruppoRepo()
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


//    private val _uiState = MutableStateFlow(StatoRegistrazioneUi())
//    val uiState: StateFlow<StatoRegistrazioneUi> = _uiState.asStateFlow()

    fun creaGruppo() {
        viewModelScope.launch {
            repository.creaGruppo()
        }
    }

//    fun invita(message: String) {
//    }

}
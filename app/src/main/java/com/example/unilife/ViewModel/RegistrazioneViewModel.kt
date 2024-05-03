package com.example.unilife.ViewModel


import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.ImpostazioniDB
import com.example.unilife.Repository.RegistrazioneRepo
import com.example.unilife.StateUI.StatoRegistrazioneUi
import com.example.unilife.View.AccessoActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await



class RegistrazioneViewModel : ViewModel() {



    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }


    private val repository = RegistrazioneRepo()


    private val _uiState = MutableStateFlow(StatoRegistrazioneUi())
    val uiState: StateFlow<StatoRegistrazioneUi> = _uiState.asStateFlow()



    fun registraUtente(email: String, password: String, username: String) {
        viewModelScope.launch {
            _uiState.value = StatoRegistrazioneUi.loading()
            val result = repository.registrazione(email, password)

            if (result.isSuccess) {
                Utente(
                    id = dbSettings.firebaseAuth.uid!!,
                    id_gruppo = "",
                    email = email,
                    password = password,
                    username = username
                ).apply {
                    dbSettings.utenteCorrenteDocRef.set(this).await()
                }
                _uiState.value = StatoRegistrazioneUi.success()
            } else {
                _uiState.value = StatoRegistrazioneUi.error(result.exceptionOrNull()!!.message!!)
            }
        }
    }
    }






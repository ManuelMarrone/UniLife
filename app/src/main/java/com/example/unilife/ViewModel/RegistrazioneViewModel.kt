package com.example.unilife.ViewModel


import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilife.Repository.ImpostazioniDB
import com.example.unilife.Repository.RegistrazioneRepo
import com.example.unilife.StateUI.StatoRegistrazioneUi
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class RegistrazioneViewModel : ViewModel() {


    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }


    private val repository = RegistrazioneRepo()
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()



    private val _uiState = MutableStateFlow(StatoRegistrazioneUi())
    val uiState: StateFlow<StatoRegistrazioneUi> = _uiState.asStateFlow()




    fun registraUtente(email: String, password: String, username: String) {
        viewModelScope.launch{
            repository.registrazione(email, password, username)
        }

    }


    suspend fun verificaUnicitaCredenziali(email: String, username: String) :Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val controllo_username = dbSettings.firestore.collection("utenti")
                    .whereEqualTo("username", username)
                    .get()
                    .await()

                val controllo_email = dbSettings.firestore.collection("email")
                    .whereEqualTo("email", email)
                    .get()
                    .await()

               controllo_username.isEmpty() && controllo_email.isEmpty()

            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "${e}")
                false
            }
        }
    }



















}

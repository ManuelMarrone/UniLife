package com.example.unilife.ViewModel


import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.ImpostazioniDB
import com.example.unilife.Repository.RegistrazioneRepo
import com.example.unilife.StateUI.StatoRegistrazioneUi
import com.example.unilife.View.AccessoActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await



class RegistrazioneViewModel : ViewModel() {


    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }


    private val repository = RegistrazioneRepo()
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


    private val _uiState = MutableStateFlow(StatoRegistrazioneUi())
    val uiState: StateFlow<StatoRegistrazioneUi> = _uiState.asStateFlow()

   /** fun fireStoreUtente(email: String, password: String, username: String) {

        val utente = Utente(
            username = username,
            email = email,
            password = password
        )
        val idUtente = dbSettings.firebaseAuth.currentUser?.providerId!!
        dbSettings.firestore.collection("utenti")
            .document(idUtente)
            .set(utente)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Added document with ID ${idUtente}")
            }
            .addOnFailureListener {
                Log.w(ContentValues.TAG, "Error adding document")
            }
    }

   fun registraUtente(email: String, password: String, username: String) {
        viewModelScope.launch {
            _uiState.value = StatoRegistrazioneUi.loading()
            val result = repository.registrazione(email, password)
            if (result.isSuccess) {
                _uiState.value = StatoRegistrazioneUi.success()
            } else {
                _uiState.value = StatoRegistrazioneUi.error(result.exceptionOrNull()!!.message!!)
            }
        }
    }*/




    fun registraUtente(email: String, password: String, username: String) {
        viewModelScope.launch{
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful()) {
                val idUtente = firebaseAuth.currentUser?.uid!!
                val documentReference: DocumentReference =
                    dbSettings.firestore.collection("utenti").document(idUtente)
                val utente = Utente(
                    username = username,
                    email = email,
                    password = password
                )
                documentReference.set(utente).addOnSuccessListener {
                    Log.d(ContentValues.TAG, "Added document with ID ${idUtente}")
                }
                    .addOnFailureListener {
                        Log.w(ContentValues.TAG, "Error adding document")
                    }
            }
        }
        }





    }

}

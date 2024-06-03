package com.example.unilife.Repository

import android.content.ContentValues
import android.util.Log
import com.example.unilife.Model.Utente
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RegistrazioneRepo() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore


    fun controlloUsername(username: String): Task<QuerySnapshot> {
       return db.collection("utenti")
           .whereEqualTo("username", username)
           .get()
    }

    fun controlloEmail(email: String): Task<QuerySnapshot>{
        return db.collection("utenti")
            .whereEqualTo("email", email)
            .get()
    }


    suspend fun registrazione(email: String, password:String, username: String):
            Result<Boolean> = withContext(Dispatchers.IO){
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful()) {
                    val idUtente = firebaseAuth.currentUser?.uid!!
                    val documentReference: DocumentReference =
                        db.collection("utenti").document(idUtente)
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
            }.await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


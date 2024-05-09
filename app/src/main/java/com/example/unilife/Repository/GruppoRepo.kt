package com.example.unilife.Repository

import android.content.ContentValues
import android.util.Log
import com.example.unilife.Model.Gruppo
import com.example.unilife.Model.Utente
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class GruppoRepo {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }

    suspend fun creaGruppo():
            Result<Boolean> = withContext(Dispatchers.IO){
        try {
            val idUtente = firebaseAuth.currentUser?.uid!!

            val partecipanti = mutableListOf<String>(idUtente)

            val gruppo = Gruppo(
                partecipanti = partecipanti,
            )
            dbSettings.firestore.collection("gruppi").add(gruppo).addOnSuccessListener {
                Log.d(ContentValues.TAG, "partecipante aggiunto ${idUtente}")
            }
            .addOnFailureListener {
                Log.w(ContentValues.TAG, "Error adding document")
            }
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
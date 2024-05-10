package com.example.unilife.Repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unilife.Model.Gruppo
import com.example.unilife.Model.Utente
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue.arrayUnion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class GruppoRepo {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }

    private val utenteRepo = UtenteRepo()
    suspend fun creaGruppo(): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val idUtente = firebaseAuth.currentUser?.uid!!
            val partecipanti = mutableListOf<String>(idUtente)
            val gruppo = Gruppo(partecipanti = partecipanti)

            val documentReference = dbSettings.firestore.collection("gruppi").add(gruppo).await()
            val nuovoGruppoId = documentReference.id
            utenteRepo.setIdGruppo(nuovoGruppoId)
            Log.d(ContentValues.TAG, "ID del nuovo gruppo: $nuovoGruppoId")

            Result.success(true)
        } catch (e: Exception) {
            Log.w(ContentValues.TAG, "Errore durante la creazione del gruppo", e)
            Result.failure(e)
        }
    }


    fun aggiungiPartecipante(idGruppo:String)
    {
        val idUtente = firebaseAuth.currentUser?.uid!!
        val gruppoReference: DocumentReference = dbSettings.firestore.collection("gruppi").document(idGruppo)

        gruppoReference.get()
            .addOnSuccessListener { documentSnapshot ->
                dbSettings.firestore.collection("gruppi").document(idGruppo).update("partecipanti" , arrayUnion(idUtente))
            }
            .addOnFailureListener { exception ->
                // Gestisci eventuali errori qui
                Log.w(ContentValues.TAG, "Errore durante la creazione del gruppo", exception)
            }

    }

    suspend fun controllaCodiceInvito(idGruppo: String, callback: (Boolean?) -> Unit) {
        try {
            val documentReference: DocumentReference = dbSettings.firestore.collection("gruppi").document(idGruppo)
            val documentSnapshot = withContext(Dispatchers.IO) {
                documentReference.get().await()
            }
            if (documentSnapshot.exists()) {
                callback(true)
            } else {
                callback(false)
            }
        } catch (e: Exception) {
            callback(false)
        }
    }


}


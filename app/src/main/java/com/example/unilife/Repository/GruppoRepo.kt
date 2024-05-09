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
}
package com.example.unilife.Repository

import android.content.ContentValues
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unilife.Model.Gruppo
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Locale
import java.util.concurrent.CompletableFuture

class UtenteRepo {

    private val dbSettings = ImpostazioniDB()
    private val firebaseAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    //metodo che restituisce il gruppo dell'utente se ne fa parte
    fun getGruppo(): MutableLiveData<String?> {
        val liveData = MutableLiveData<String?>()
        val idUtente = firebaseAuth.currentUser?.uid!!
        val documentReference: DocumentReference =dbSettings.firestore.collection("utenti").document(idUtente)

        documentReference.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val idGruppo = documentSnapshot.getString("id_gruppo")
                    liveData.value = idGruppo
                } else {
                    //documento non esiste
                }
            }
            .addOnFailureListener { exception ->
                // Gestisci eventuali errori qui
            }
        return liveData
    }

    fun getIdGruppo(callback: (String?) -> Unit) {
        val idUtente = firebaseAuth.currentUser?.uid ?: return

        val documentReference: DocumentReference = dbSettings.firestore.collection("utenti").document(idUtente)

        documentReference.get()
            .addOnSuccessListener { documentSnapshot ->
                val idGruppo = documentSnapshot.getString("id_gruppo")
                callback(idGruppo)
            }
            .addOnFailureListener { exception ->
                // Gestisci eventuali errori qui
                Log.e("getIdGruppo", "Errore durante la lettura del documento", exception)
                callback(null)
            }
    }


    suspend fun getUsernamePartecipanti(callback: (ArrayList<String>?) -> Unit) {
        val idUtente = firebaseAuth.currentUser?.uid ?: return
        val documentReference: DocumentReference = dbSettings.firestore.collection("utenti").document(idUtente)

        val partecipantiList = ArrayList<String>()

        try {
            val documentSnapshot = documentReference.get().await()
            val idGruppo = documentSnapshot.getString("id_gruppo")

            if (idGruppo != null) {
                val querySnapshot = dbSettings.firestore.collection("utenti")
                    .whereEqualTo("id_gruppo", idGruppo)
                    .get().await()

                for (document in querySnapshot.documents) {
                    val username = document.getString("username")
                    if (username != null) {
                        partecipantiList.add(username)
                    }
                }
                callback(partecipantiList)
            } else {
                callback(null)
            }
        } catch (e: Exception) {
            Log.e("getUsernamePartecipanti", "Errore durante il recupero degli utenti", e)
            callback(null)
        }
    }



    //funzione per aggiornare l'idGruppo al quale fa parte o meno un utente
    fun setIdGruppo(idGruppo:String)
    {
        val idUtente = firebaseAuth.currentUser?.uid!!
        dbSettings.firestore.collection("utenti").document(idUtente).update("id_gruppo" , idGruppo)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "id settato ${idGruppo}")
            }
            .addOnFailureListener {
                Log.w(ContentValues.TAG, "Errore id non settato")
            }
    }



    fun getIdUtenteDaUsername(username: String, callback: (String?) -> Unit) {
        db.collection("utenti")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // Se troviamo un documento con l'username corrispondente, otteniamo l'ID dell'utente
                    val idUtente = querySnapshot.documents[0].id
                    callback(idUtente)
                } else {
                    Log.d("getIdUtenteDaUsername", "Nessun utente trovato con username $username")
                    callback(null) // Indica che l'utente non è stato trovato
                }
            }
            .addOnFailureListener { e ->
                Log.w("getIdUtenteDaUsername", "Errore durante la ricerca dell'utente con username $username", e)
                callback(null) // Indica che si è verificato un errore
            }
    }

    fun setIdGruppoDaUsername(username: String) {
        getIdUtenteDaUsername(username){
            idUtente ->
            // Aggiorniamo il campo 'id_gruppo' nel documento dell'utente
            db.collection("utenti").document(idUtente.toString())
                .update("id_gruppo", null)
                .addOnSuccessListener {
                    Log.d("eliminaPartecipante", "id settato per l'utente $idUtente")
                }
                .addOnFailureListener { e ->
                    Log.w("eliminaPartecipante", "Errore id non settato per l'utente $idUtente", e)
                }
        }

    }



    fun logOut() {
        firebaseAuth.signOut()
    }

}
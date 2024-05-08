package com.example.unilife.Repository

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unilife.Model.Gruppo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentReference
import java.util.Locale

class UtenteRepo {

    private val dbSettings = ImpostazioniDB()
    private val firebaseAuth = FirebaseAuth.getInstance()

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

    fun logOut() {
        firebaseAuth.signOut()
    }

}
package com.example.unilife.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.unilife.Model.Utente
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class UtenteRepo {

    private val dbSettings = ImpostazioniDB()
    private val firebaseAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()


    fun getUtente(): Task<DocumentSnapshot> {
        val idUtente = firebaseAuth.currentUser!!.uid
        val documentReference = dbSettings.firestore.collection("utenti").document(idUtente)
        return documentReference.get()
    }
    fun eliminaUtenteFireStore(): Task<Void> {
        val idUtente = firebaseAuth.currentUser!!.uid
        val documentReference = dbSettings.firestore.collection("utenti").document(idUtente)
        return documentReference.delete()
    }

    fun eliminaUtenteAuth(): Task<Void> {
        val user = Firebase.auth.currentUser!!

        return user.delete()
    }

    fun getUtenteLive(): DocumentReference {
        val idUtente = firebaseAuth.currentUser!!.uid
        val documentReference = dbSettings.firestore.collection("utenti").document(idUtente)
        return documentReference
    }



    //funzione per aggiornare l'idGruppo al quale fa parte o meno un utente
    fun setIdGruppo(idGruppo: String): Task<Void> {
        val idUtente = firebaseAuth.currentUser?.uid!!
        return dbSettings.firestore.collection("utenti").document(idUtente)
            .update("id_gruppo" , idGruppo)
    }


    fun getIdUtenteDaUsername(username: String): Task<QuerySnapshot> {
        val documentReference = dbSettings.firestore.collection("utenti").whereEqualTo("username", username).get()
        Log.d("Rimozione partecipanti", "document ${documentReference}")

        return documentReference
    }

    fun setIdGruppoByIdUtente(idUtente: String): Task<Void> {
        val utenteDoc = db.collection("utenti").document(idUtente)
        return utenteDoc.update("id_gruppo", null)
    }

    fun aggiornaUsername(user:String): Task<Void> {
        val userId = firebaseAuth.currentUser?.uid
        return db.collection("utenti").document(userId!!).update("username", user)
    }

    fun aggiornaPassword(pwd:String): Task<Void> {
        val user = firebaseAuth.currentUser
        return user!!.updatePassword(pwd)
    }

    fun aggiornaPasswordFireStore(pwd:String): Task<Void> {
        val userId = firebaseAuth.currentUser?.uid!!
        return db.collection("utenti").document(userId).update("password", pwd)

    }

    fun unicitaUsername(username:String): Task<QuerySnapshot> {
        return dbSettings.firestore.collection("utenti").whereEqualTo("username", username)
              .get()

    }


    fun logOut() {
        firebaseAuth.signOut()
    }

}
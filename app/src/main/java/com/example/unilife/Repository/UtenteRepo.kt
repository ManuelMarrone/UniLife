package com.example.unilife.Repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot

class UtenteRepo {

    private val dbSettings = ImpostazioniDB()
    val firebaseAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    private val idUtente = firebaseAuth.currentUser?.uid!!
    private val utenteRef = db.collection("utenti").document(idUtente)
    private var listenerRegistration : ListenerRegistration? = null


    fun getUtente(): Task<DocumentSnapshot> {
        val idUtente = firebaseAuth.currentUser!!.uid
        val documentReference = dbSettings.firestore.collection("utenti").document(idUtente)
        return documentReference.get()
    }

    fun getUtenteLive(): DocumentReference {
        val idUtente = firebaseAuth.currentUser!!.uid
        val documentReference = dbSettings.firestore.collection("utenti").document(idUtente)
        return documentReference
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

    fun aggiornaUsername(user:String): Task<Void>? {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null)
        {
            return db.collection("utenti").document(userId).update("username", user)

        }
        else
        {
            return null
        }
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


    fun updateUsernameInFirestore(userId: String, newUsername: String): Task<Void> {
        return db.collection("utenti").document(userId).update("username", newUsername)
    }

    fun updatePasswordInFirestore(userId: String, newPassword: String): Task<Void> {
        return db.collection("utenti").document(userId).update("password", newPassword)
    }

    fun updateEmailInFirestore(userId: String, newEmail: String): Task<Void> {
        return db.collection("utenti").document(userId).update("email", newEmail)
    }

    fun updateUserPassword(newPassword: String): Task<Void> {
        val user = firebaseAuth.currentUser
        return user?.updatePassword(newPassword) ?: Tasks.forException(Exception("User not authenticated"))
    }

    fun updateUserEmail(newEmail: String): Task<Void> {
        val user = firebaseAuth.currentUser
        return user?.updateEmail(newEmail) ?: Tasks.forException(Exception("User not authenticated"))
    }


    fun logOut() {
        firebaseAuth.signOut()
    }


}
package com.example.unilife.Repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore


class UtenteRepo {


    val firebaseAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    private val fire = Firebase.firestore

    //private val idUtente = firebaseAuth.currentUser?.uid!!



    fun getUtente(): Task<DocumentSnapshot>? {
        val currentUser = firebaseAuth.currentUser
        return if (currentUser != null) {
            val idUtente = currentUser.uid
            val documentReference = fire.collection("utenti").document(idUtente)
            documentReference.get()
        } else {
            Log.e("UtenteRepo", "User is not authenticated")
            null
        }
    }

    fun getUtenteLive(): DocumentReference? {
        val currentUser = firebaseAuth.currentUser
        return if (currentUser != null) {
            val idUtente = currentUser.uid
            val documentReference = fire.collection("utenti").document(idUtente)
            documentReference
        } else {
            Log.e("UtenteRepo", "User is not authenticated")
            null
        }
    }


    fun eliminaUtenteFireStore(): Task<Void> {
        Log.d("Rimozione utente", "firestore")
        val idUtente = firebaseAuth.currentUser!!.uid
        Log.d("Rimozione utente", "id ${idUtente}")
        val documentReference = fire.collection("utenti").document(idUtente)
        Log.d("Rimozione utente", "docu ${documentReference}")
        return documentReference.delete()
    }

    fun eliminaUtenteAuth(): Task<Void> {
        val user = Firebase.auth.currentUser!!

        return user.delete()
    }

    fun aggiornaUsername(user:String): Task<Void>? {
        val userId = firebaseAuth.currentUser?.uid
        return if (userId != null) {
            db.collection("utenti").document(userId).update("username", user)

        } else {
            null
        }
    }

    fun aggiornaPassword(pwd:String): Task<Void>? {
        val user = firebaseAuth.currentUser
        return user?.updatePassword(pwd)
    }

    fun aggiornaPasswordFireStore(pwd:String): Task<Void> {
        val userId = firebaseAuth.currentUser?.uid!!
        return db.collection("utenti").document(userId).update("password", pwd)

    }

    fun unicitaUsername(username:String): Task<QuerySnapshot> {
        return fire.collection("utenti").whereEqualTo("username", username)
            .get()

    }


    //funzione per aggiornare l'idGruppo al quale fa parte o meno un utente
    fun setIdGruppo(idGruppo: String): Task<Void> {
        val idUtente = firebaseAuth.currentUser?.uid!!
        return fire.collection("utenti").document(idUtente)
            .update("id_gruppo" , idGruppo)
    }


    fun getIdUtenteDaUsername(username: String): Task<QuerySnapshot> {
        val documentReference = fire.collection("utenti").whereEqualTo("username", username).get()
        Log.d("Rimozione partecipanti", "document ${documentReference}")

        return documentReference
    }

    fun setIdGruppoByIdUtente(idUtente: String): Task<Void> {
        val utenteDoc = db.collection("utenti").document(idUtente)
        return utenteDoc.update("id_gruppo", null)
    }


    fun logOut() {
        firebaseAuth.signOut()
    }


}
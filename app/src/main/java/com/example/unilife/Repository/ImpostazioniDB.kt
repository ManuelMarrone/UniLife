package com.example.unilife.Repository

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class ImpostazioniDB {
    companion object {
            private const val UTENTI_COLLECTION = "utenti"
            private const val GRUPPI_COLLECTION = "gruppi"

    }
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firestore = Firebase.firestore
    lateinit var utenteCorrenteDocRef: DocumentReference
    lateinit var utentiCorrenteCollectionRef: CollectionReference
    val utentiCollectionRef = firestore.collection(UTENTI_COLLECTION)
    val gruppiCollectionRef = firestore.collection(GRUPPI_COLLECTION)
    init {
        firebaseAuth.currentUser?.let()
    { user ->
        utenteCorrenteDocRef = utentiCollectionRef.document(user.uid)

    }
    }
}









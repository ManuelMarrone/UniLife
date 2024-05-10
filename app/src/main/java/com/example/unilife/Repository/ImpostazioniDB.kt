package com.example.unilife.Repository

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class ImpostazioniDB {

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firestore = Firebase.firestore

    val db = FirebaseFirestore.getInstance()

}









package com.example.unilife.Repository

import com.example.unilife.Model.Documento
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage


class ArchivioRepo {

    private val db =  Firebase.firestore
    val storage = Firebase.storage

    private val firebaseAuth = FirebaseAuth.getInstance()


    fun getUtente(): Task<DocumentSnapshot> {
        val idUtente = firebaseAuth.currentUser!!.uid
        val documentReference = db.collection("utenti").document(idUtente)
        return documentReference.get()
    }


    fun deleteFile(groupId: String, documentId: String): Task<Void> {
        // Elimina il documento da Firestore
        val deleteTask = db.collection("gruppi").document(groupId).collection("documenti").document(documentId).delete()
        return deleteTask
    }

    fun deleteFileFromStorage(groupId: String, fileName: String): Task<Void> {
        // Elimina il documento dallo Storage
        val fileRef = storage.reference.child("documents/$groupId/$fileName")
        return fileRef.delete()
    }

    fun getFirestoreCollection(groupId: String): CollectionReference {
        return db.collection("gruppi").document(groupId).collection("documenti")
    }

    fun saveDocumentToFirestore(documentPath: String, documento: Documento): Task<Void> {
        return FirebaseFirestore.getInstance().document(documentPath).set(documento)
    }


}












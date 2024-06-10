package com.example.unilife.Repository

import android.util.Log
import com.example.unilife.Model.Documento
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage


class ArchivioRepo {

    private val db =  Firebase.firestore
    private val storage = Firebase.storage

    private val firebaseAuth = FirebaseAuth.getInstance()


    fun getUtente(): Task<DocumentSnapshot> {
        val idUtente = firebaseAuth.currentUser!!.uid
        val documentReference = db.collection("utenti").document(idUtente)
        return documentReference.get()
    }


    fun deleteFile(groupId: String, documentId: String): Task<Void> {
        //elimina il documento da Firestore
        val deleteTask = db.collection("gruppi").document(groupId).collection("documenti").document(documentId).delete()
        return deleteTask
    }

    fun deleteFileFromStorage(groupId: String, fileName: String): Task<Void> {
        //elimina il documento dallo Storage
        val fileRef = storage.reference.child("documents/$groupId/$fileName")
        return fileRef.delete()
    }

    fun getFirestoreCollection(groupId: String): CollectionReference {
        return db.collection("gruppi").document(groupId).collection("documenti")
    }

    fun saveDocumentToFirestore(documentPath: String, documento: Documento): Task<Void> {
        return FirebaseFirestore.getInstance().document(documentPath).set(documento)
    }

    fun eliminaStorage(idGruppo: String) {
        val fileRef = storage.reference.child("documents/$idGruppo")

        fileRef.listAll()
            .addOnSuccessListener { listResult ->
                val items = listResult.items
                val deleteTasks = items.map { it.delete() }

                Tasks.whenAll(deleteTasks)
                    .addOnSuccessListener {
                        Log.d("EliminaStorage", "Tutti i file sono stati eliminati con successo.")
                    }
                    .addOnFailureListener { e ->
                        Log.w("EliminaStorage", "Errore durante l'eliminazione dei file", e)
                    }
            }
            .addOnFailureListener { e ->
                Log.w("EliminaStorage", "Errore durante il recupero dei file", e)
            }
    }

    fun eliminaRaccolta(groupId: String){
        val fileRef = db.collection("gruppi").document(groupId).collection("documenti")
        fileRef.get()
            .addOnSuccessListener { documents ->
                val batch = db.batch()
                for (document in documents) {
                    batch.delete(document.reference)
                }
                batch.commit()
                    .addOnSuccessListener {
                        Log.d("EliminaCollezione", "Tutti i documenti eliminati con successo.")
                    }
                    .addOnFailureListener { e ->
                        Log.w("EliminaCollezione", "Errore durante l'eliminazione dei documenti", e)
                    }
            }

    }


}
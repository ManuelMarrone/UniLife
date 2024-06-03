package com.example.unilife.Repository

import com.example.unilife.Model.Documento
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class ArchivioRepo {


    private val firestore = FirebaseFirestore.getInstance()
    private val db =  Firebase.firestore

    private val firebaseAuth = FirebaseAuth.getInstance()





    fun getUtente(): Task<DocumentSnapshot> {
        val idUtente = firebaseAuth.currentUser!!.uid
        val documentReference = db.collection("utenti").document(idUtente)
        return documentReference.get()
    }


    fun deleteFile(groupId: String, documentId: String): Task<Void> {
        // Elimina il documento da Firestore
        val deleteTask = db.collection("gruppi").document(groupId).collection("documenti").document(documentId).delete()

        // Elimina il documento dallo Storage (da implementare)
        // Esempio:
        // val storageRef = storage.reference.child("gruppi/$groupId/documenti/$documentId")
        // return storageRef.delete()

        return deleteTask
    }

    fun getFirestoreCollection(groupId: String): CollectionReference {
        return db.collection("gruppi").document(groupId).collection("documenti")
    }

    fun saveDocumentToFirestore(documentPath: String, documento: Documento): Task<Void> {
        return FirebaseFirestore.getInstance().document(documentPath).set(documento)
    }

   /** fun fetchDocumenti(groupId: String): Task<QuerySnapshot> {
        val documentiRef = getFirestoreCollection(groupId)
        return documentiRef.get()
    }*/


    /**
    suspend fun uploadFile(byteArray: ByteArray, uri: Uri) {
        // Implementazione dell'upload del file su Firebase Storage
        val storageRef = storage.reference
        val id_doc = getID_doc()
        if (id_doc != null) {
            storageRef.child("documents/$id_doc/${Date().time}.bin").putBytes(byteArray)
                .addOnSuccessListener {
                    Log.d("upload", "File uploaded successfully")
                    saveDocumentToFirestore(uri.lastPathSegment ?: "uploaded_file", id_doc)
                }
                .addOnFailureListener {
                    Log.d("upload", "File upload failed")
                }
        } else {
            Log.e("GroupID", "Group ID is null")
        }
    }*/

    /**
    suspend fun getID_doc(): String? = withContext(Dispatchers.IO) {
        val utenteId = auth.currentUser?.uid
        var groupId: String? = null
        if (utenteId != null) {
            val db = FirebaseFirestore.getInstance()
            try {
                val document = db.collection("utenti").document(utenteId).get().await()
                val userGroup = document.getString("id_gruppo")
                groupId = userGroup
            } catch (e: Exception) {
                Log.e("GroupID", "Error getting document", e)
            }
        } else {
            Log.e("GroupID", "User ID is null")
        }
        groupId
    }

*/




}


    /**




*/












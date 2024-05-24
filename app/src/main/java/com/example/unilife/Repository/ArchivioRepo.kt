package com.example.unilife.Repository

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unilife.Model.Documento
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Date









class ArchivioRepo {

    private val storage = FirebaseStorage.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _lista_documenti : MutableLiveData<ArrayList<Documento>> = MutableLiveData()
    val lista_documenti: LiveData<ArrayList<Documento>> = _lista_documenti





    fun getUtente(): Task<DocumentSnapshot> {
        val idUtente = firebaseAuth.currentUser!!.uid
        val documentReference = dbSettings.firestore.collection("utenti").document(idUtente)
        return documentReference.get()
    }

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












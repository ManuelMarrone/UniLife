package com.example.unilife.ViewModel

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilife.Model.Documento
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.ArchivioRepo
//import com.example.unilife.Repository.ArchivioRepo
import com.example.unilife.Repository.ImpostazioniDB
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import java.util.Date
import java.util.jar.Manifest


class ArchivioViewModel :ViewModel() {

    private val storage = FirebaseStorage.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val dbSettings: ImpostazioniDB by lazy { ImpostazioniDB() }
    private val auth = FirebaseAuth.getInstance()
    private val archivioRepo = ArchivioRepo()
    private var idGruppo : String? = null
    private val _lista_documenti : MutableLiveData<ArrayList<Documento>> = MutableLiveData()
    val lista_documenti: LiveData<ArrayList<Documento>> = _lista_documenti



    init {
        getIdGruppoUtente()
    }


    fun getIdGruppoUtente()
    {
        archivioRepo.getUtente().addOnSuccessListener { utente ->
            idGruppo = utente.toObject(Utente::class.java)?.id_gruppo
            Log.d("inizializza", "idGruppo ${idGruppo}")

        }
    }
    fun uploadFile(byteArray: ByteArray, uri: Uri) {
        // Implementazione dell'upload del file su Firebase Storage
        val storageRef = storage.reference

        if (idGruppo != null) {
            storageRef.child("documents/$idGruppo/${Date().time}.bin").putBytes(byteArray)
                .addOnSuccessListener {
                    Log.d("upload", "File uploaded successfully")
                    saveDocumentToFirestore(uri.lastPathSegment ?: "uploaded_file", idGruppo.toString())
                }
                .addOnFailureListener {
                    Log.d("upload", "File upload failed")
                }
        } else {
            Log.e("GroupID", "Group ID is null")
        }
    }

    /**
        fun uploadFile(byteArray: ByteArray, uri: Uri) {

            viewModelScope.launch {
                archivioRepo.uploadFile(byteArray, uri)
            }
        }*/




    fun saveDocumentToFirestore(fileName: String, id_doc: String) {
        // Implementazione del salvataggio dei metadati del documento su Firestore
        val documento = Documento(
            nome_doc = fileName,
            id_documento = id_doc
        )
        firestore.collection("documenti").add(documento)
            .addOnSuccessListener {
                Log.d("Firestore", "File metadata saved successfully in 'documenti' collection")
            }
            .addOnFailureListener { e ->
                Log.d("Firestore", "Failed to save file metadata in 'documenti' collection", e)
            }
    }

}





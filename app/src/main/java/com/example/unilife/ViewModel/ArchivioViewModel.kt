package com.example.unilife.ViewModel

//import com.example.unilife.Repository.ArchivioRepo
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unilife.Model.Documento
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.ArchivioRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.Date


class ArchivioViewModel :ViewModel() {

    private val storage = FirebaseStorage.getInstance()
    private val archivioRepo = ArchivioRepo()
    private var idGruppo : String? = null
    private val _lista_documenti = MutableLiveData<List<Documento>>()
    val lista_documenti: LiveData<List<Documento>> get() = _lista_documenti



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
            val fileRef = storageRef.child("documents/$idGruppo/${Date().time}.bin")
            fileRef.putBytes(byteArray)
                .addOnSuccessListener {
                    Log.d("upload", "File uploaded successfully")

                    // Ottenere l'URL di download del file caricato
                    fileRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        val fileUrl = downloadUri.toString()
                        saveDocumentToFirestore(uri.lastPathSegment ?: "uploaded_file", idGruppo.toString(), fileUrl)
                    }.addOnFailureListener {
                        Log.d("upload", "Failed to get download URL")
                    }
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




    fun saveDocumentToFirestore(fileName: String, id_doc: String, url: String) {
        // Implementazione del salvataggio dei metadati del documento su Firestore
        val documento = Documento(
            nome_doc = fileName,
            id_documento = id_doc,
            url = url
        )

        /**Non va bene, le chiamate al DB vanno nel REPOSITORY**/


//        firestore.collection("documenti").document(id_doc).set(documento)
//            .addOnSuccessListener {
//                Log.d("Firestore", "File metadata saved successfully in 'documenti' collection")
//            }
//            .addOnFailureListener { e ->
//                Log.d("Firestore", "Failed to save file metadata in 'documenti' collection", e)
//            }
    }
/**
    fun addDocumentToList(documento: Documento) {
        val currentList = _lista_documenti.value ?: mutableListOf()
        currentList.add(documento)
        _lista_documenti.postValue(currentList)
    }

    fun loadDocument(groupId: String) {
        firestore.collection("documenti").document(groupId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val documento = document.toObject(Documento::class.java)
                    documento?.let {
                        addDocumentToList(it)
                    }
                } else {
                    Log.d("Firestore", "Document not found")
                }
            }
            .addOnFailureListener { e ->
                Log.d("Firestore", "Failed to load document", e)
            }
    }

    fun fetchDocumenti() {
        firestore.collection("documenti")
            .get()
            .addOnSuccessListener { result ->
                val documenti = mutableListOf<Documento>()
                for (document in result) {
                    val id = document.id
                    val nomeDoc = document.getString("nome_doc") ?: ""
                    documenti.add(Documento(nomeDoc, id))
                }
                _lista_documenti.postValue(documenti)
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "Failed to fetch documents", exception)
            }
    }*/
}





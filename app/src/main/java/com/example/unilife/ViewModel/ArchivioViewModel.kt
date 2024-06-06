package com.example.unilife.ViewModel


import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unilife.Model.Documento
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.ArchivioRepo
import com.example.unilife.Repository.UtenteRepo
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.FirebaseStorage
import java.util.Date


class ArchivioViewModel :ViewModel() {

    private val utenteRepo = UtenteRepo()
    private val storage = FirebaseStorage.getInstance()
    private val archivioRepo = ArchivioRepo()
    var idGruppoRecuperato = false
    private var idGruppo : String? = null
    private val _idGruppoUtente = MutableLiveData<String>().apply { value = "" }
    val idGruppoUtente: LiveData<String> = _idGruppoUtente


    private val _lista_documenti : MutableLiveData<List<Documento>?> = MutableLiveData()
    val lista_documenti: LiveData<List<Documento>?> = _lista_documenti

    init {
        getIdGruppoUtente()
        getIdGruppo()
    }


    fun getIdGruppoUtente()
    {
        utenteRepo.getUtente().addOnSuccessListener { utente ->
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


    fun saveDocumentToFirestore(fileName: String, id_doc: String, url: String) {
        // Implementazione del salvataggio dei metadati del documento su Firestore
        val documento = Documento(
            nome_doc = fileName,
            id_documento = id_doc,
            url = url
        )

        val documentPath = "gruppi/$idGruppo/documenti/$id_doc"
        archivioRepo.saveDocumentToFirestore(documentPath, documento)
            .addOnSuccessListener {
                Log.d("Firestore", "File metadata saved successfully in 'documenti' collection")
            }
            .addOnFailureListener { e ->
                Log.d("Firestore", "Failed to save file metadata in 'documenti' collection", e)
            }
    }

    fun eliminaDocumento(groupId: String, documentId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        archivioRepo.deleteFile(groupId, documentId)
            .addOnSuccessListener {
                Log.d("eliminazione", "Eliminazione del documento $documentId completata con successo")
                onSuccess.invoke()
            }
            .addOnFailureListener { e ->
                Log.e("eliminazione", "Errore durante l'eliminazione del documento $documentId: $e")
                onFailure.invoke(e)
            }
    }



fun getAllDocument(
    firestore: CollectionReference,
    onSuccess: (List<Documento>) -> Unit,
    onFailure: (String) -> Unit
) {
    firestore.addSnapshotListener { snapshot, error ->
        if (error != null) {
            onFailure(error.message.toString())
            return@addSnapshotListener
        }

        if (snapshot != null && !snapshot.isEmpty) {
            val tempList = mutableListOf<Documento>()
            for (document in snapshot.documents) {
                val pdfFile = document.toObject(Documento::class.java)
                pdfFile?.let {
                    tempList.add(pdfFile)
                }
            }
            onSuccess(tempList)
        } else {
            onFailure("No data found")
        }
    }
}



    fun getFirestoreCollection(groupId: String): CollectionReference {
        return archivioRepo.getFirestoreCollection(groupId)
    }

    fun getIdGruppo() {
        utenteRepo.getUtente().addOnSuccessListener { utente ->
            val idGruppo = utente.toObject(Utente::class.java)?.id_gruppo
            _idGruppoUtente.postValue(idGruppo)
            idGruppoRecuperato = true
            Log.d("inizializza", "idGruppo $idGruppo")
        }.addOnFailureListener { e ->
            Log.e("getIdGruppoUtente", "Errore durante il recupero dell'utente: ${e.message}")
        }
    }

    }






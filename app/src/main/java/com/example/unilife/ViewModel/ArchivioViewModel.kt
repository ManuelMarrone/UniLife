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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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



        fun uploadFile(byteArray: ByteArray, uri: Uri) {

            viewModelScope.launch {
                archivioRepo.uploadFile(byteArray, uri)
            }
        }
    }





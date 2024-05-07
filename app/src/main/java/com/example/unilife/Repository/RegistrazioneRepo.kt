package com.example.unilife.Repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await

class RegistrazioneRepo() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun registrazione(email: String, password:String):
            Result<Boolean> = withContext(Dispatchers.IO){
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Log.d(ContentValues.TAG, "ok")
            Result.success(true)
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "error ${e}")
            Result.failure(e)
        }
    }
}


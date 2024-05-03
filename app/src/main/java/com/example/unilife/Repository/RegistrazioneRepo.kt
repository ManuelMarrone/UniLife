package com.example.unilife.Repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await

class RegistrazioneRepo() {

    private val firebaseAuth = FirebaseAuth.getInstance()



    suspend fun registrazione(email: String, password:String):
            Result<Boolean> = withContext(Dispatchers.IO){

        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    }


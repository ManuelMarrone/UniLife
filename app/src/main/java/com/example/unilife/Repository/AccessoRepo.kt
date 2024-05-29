package com.example.unilife.Repository

import android.content.ContentValues
import android.util.Log
import com.example.unilife.databinding.ActivityAccessoBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AccessoRepo {
    private val auth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityAccessoBinding


    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    suspend fun accesso(email:String, password:String): Result<Boolean> =
        withContext(Dispatchers.IO){
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                Result.success(true)
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Login error: ${e.message}", e)
                Result.failure(e)
            }
        }

}
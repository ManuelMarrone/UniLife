package com.example.unilife.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.unilife.Repository.AccessoRepo

class AccessoViewModel: ViewModel() {
    private val accessoRepo = AccessoRepo()

    fun isLoggedIn(): Boolean {
        return accessoRepo.isLoggedIn()
    }

    fun accedi(email: String, password: String) {
        accessoRepo.accesso(email, password)
            .addOnFailureListener{e->
            Log.d("Login","accesso fallito ${e}")
        }
    }

}
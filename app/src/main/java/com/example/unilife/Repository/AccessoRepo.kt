package com.example.unilife.Repository

import com.google.firebase.auth.FirebaseAuth

class AccessoRepo {
    private val auth = FirebaseAuth.getInstance()


    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
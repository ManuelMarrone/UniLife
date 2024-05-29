package com.example.unilife.Repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AccessoRepo {
    private val auth = FirebaseAuth.getInstance()

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun accesso(email:String, password:String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }


}
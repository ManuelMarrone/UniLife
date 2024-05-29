package com.example.unilife.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ModificaViewModel: ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun updateUsername(newUsername: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("utenti").document(userId).update("username", newUsername)
                .addOnSuccessListener {
                    Log.d("modifyUsername", "Username updated successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("modifyUsername", "${e.message}")
                }
        }
    }

    fun updatePassword(newPassword: String) {
        val user = auth.currentUser
        user?.let {
            it.updatePassword(newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        updatePasswordInFirestore(newPassword)
                        Log.d("modifypass", "Password modificata")
                    } else {
                        Log.e("modifypass", "${task.exception?.message}")
                            }
                }
        }
    }



    private fun updatePasswordInFirestore(newPassword: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("utenti").document(userId).update("password", newPassword)
                .addOnSuccessListener {
                    Log.d("modify", "Password updated successfully")

                }
                .addOnFailureListener { e ->
                    Log.e("modify", "${e.message}")
                     }
        }
    }

}
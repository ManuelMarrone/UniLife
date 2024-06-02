package com.example.unilife.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.unilife.Repository.UtenteRepo
import com.example.unilife.StateUI.AccountUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountViewModel:ViewModel() {


    // StateFlow per la gestione dello stato dell'account
    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val repo: UtenteRepo by lazy { UtenteRepo() }


    // Repository
    private val userRepository = UtenteRepo()

    /**
     * Metodo per il logout
     */
    fun logOut() {
        userRepository.logOut()
        _uiState.value = AccountUiState.logout()
    }

    fun updateUsername(newUsername: String) {
        val userId = repo.firebaseAuth.currentUser?.uid
        if (userId != null) {
            repo.updateUsernameInFirestore(userId, newUsername)
                .addOnSuccessListener {
                    Log.d("modifyUsername", "Username updated successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("modifyUsername", "${e.message}")
                }
        }
    }



    fun updatePassword(newPassword: String) {
        repo.updateUserPassword(newPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = repo.firebaseAuth.currentUser?.uid
                    if (userId != null) {
                        repo.updatePasswordInFirestore(userId, newPassword)
                            .addOnSuccessListener {
                                Log.d("modify", "Password updated successfully")
                            }
                            .addOnFailureListener { e ->
                                Log.e("modify", "${e.message}")
                            }
                    }
                    Log.d("modifypass", "Password modificata")
                } else {
                    Log.e("modifypass", "${task.exception?.message}")
                }
            }
    }


    fun updateEmail(newEmail: String) {
        repo.updateUserEmail(newEmail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = repo.firebaseAuth.currentUser?.uid
                    if (userId != null) {
                        repo.updateEmailInFirestore(userId, newEmail)
                            .addOnSuccessListener {
                                Log.d("modify", "Email updated successfully")
                            }
                            .addOnFailureListener { e ->
                                Log.e("modify", "${e.message}")
                            }
                    }
                    Log.d("modifyEmail", "Email modificata")
                } else {
                    Log.e("modifyEmail", "${task.exception?.message}")
                }
            }
    }


    fun updatePasswordInFirestore(userId: String, newPassword: String) {
        repo.updatePasswordInFirestore(userId, newPassword)
            .addOnSuccessListener {
                Log.d("modifyPassword", "Password updated successfully in Firestore")
            }
            .addOnFailureListener { e ->
                Log.e("modifyPassword", "${e.message}")
            }
    }

    fun updateEmailInFirestore(userId: String, newEmail: String) {
        repo.updateEmailInFirestore(userId, newEmail)
            .addOnSuccessListener {
                Log.d("modifyEmail", "Email updated successfully in Firestore")
            }
            .addOnFailureListener { e ->
                Log.e("modifyEmail", "${e.message}")
            }
    }

    fun isUsernameUnique(username: String, onSuccess: () -> Unit, onError: () -> Unit) {
        db.collection("utenti")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    onSuccess.invoke()
                } else {
                    onError.invoke()
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "${e.message}")
            }
    }

}
package com.example.unilife.ViewModel

import androidx.lifecycle.ViewModel
import com.example.unilife.Repository.AccessoRepo

class AccessoViewModel: ViewModel() {
    private val repository = AccessoRepo()

    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }
}
package com.example.unilife.ViewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilife.Repository.AccessoRepo
import com.example.unilife.StateUI.StatoRegistrazioneUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccessoViewModel: ViewModel() {
    private val repository = AccessoRepo()
    private val _uiState = MutableStateFlow(StatoRegistrazioneUi())
    val uiState: StateFlow<StatoRegistrazioneUi> = _uiState.asStateFlow()

    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }

    fun accedi(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = StatoRegistrazioneUi.loading()
            val result = repository.accesso(email, password)
            if (result.isSuccess) {
                Log.d(ContentValues.TAG, "Accesso eseguito")
                _uiState.value = StatoRegistrazioneUi.success()
            } else {
                Log.d(ContentValues.TAG, "Accesso fallito")
                _uiState.value = StatoRegistrazioneUi.error(result.exceptionOrNull()!!.message!!)
            }
        }
    }

}
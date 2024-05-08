package com.example.unilife.ViewModel

import androidx.lifecycle.ViewModel
import com.example.unilife.Repository.UtenteRepo
import com.example.unilife.StateUI.AccountUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountViewModel:ViewModel() {
    // StateFlow per la gestione dello stato dell'account
    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    // Repository
    private val userRepository = UtenteRepo()

    /**
     * Metodo per il logout
     */
    fun logOut() {
        userRepository.logOut()
        _uiState.value = AccountUiState.logout()
    }
}
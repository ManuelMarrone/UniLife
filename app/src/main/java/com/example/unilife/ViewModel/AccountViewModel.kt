package com.example.unilife.ViewModel

import androidx.lifecycle.ViewModel
import com.example.unilife.Repository.UtenteRepo
<<<<<<< HEAD

class AccountViewModel:ViewModel() {
    // StateFlow per la gestione dello stato dell'account
    private val _logOut = MutableLiveData<Boolean>()
    val logOut: LiveData<Boolean> get() = _logOut
    private var _utente = MutableLiveData<Utente>()
    val utente: LiveData<Utente> get() = _utente
    private var _isUnico = MutableLiveData<Boolean>()
    val isUnico: LiveData<Boolean> get() = _isUnico
=======
import com.example.unilife.StateUI.AccountUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountViewModel:ViewModel() {
    // StateFlow per la gestione dello stato dell'account
    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()
>>>>>>> parent of 3e92c37 (gestione account)

    // Repository
    private val userRepository = UtenteRepo()


     //Metodo per il logout
    fun logOut() {
        userRepository.logOut()
        _logOut.value = true
    }
}
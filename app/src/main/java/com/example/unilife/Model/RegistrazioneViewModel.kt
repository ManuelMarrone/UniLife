package com.example.unilife.Model


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilife.Repository.RegistrazioneRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class RegistrazioneViewModel : ViewModel() {

    private val dbSettings: DatabaseSettings by lazy { DatabaseSettings() }


    private val repository = RegistrazioneRepo()


    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()



    }


}
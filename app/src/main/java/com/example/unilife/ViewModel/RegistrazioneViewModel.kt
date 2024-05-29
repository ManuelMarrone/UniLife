package com.example.unilife.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilife.Repository.RegistrazioneRepo
import com.example.unilife.Repository.UtenteRepo
import kotlinx.coroutines.launch


class RegistrazioneViewModel : ViewModel() {

    private val repository = RegistrazioneRepo()
    private val utenteRepo = UtenteRepo()
    private var _userUnico = MutableLiveData<Boolean>()
    val userUnico: LiveData<Boolean> get() = _userUnico
    private var _emailUnica = MutableLiveData<Boolean>()
    val emailUnica: LiveData<Boolean> get() = _emailUnica

    fun registraUtente(email: String, password: String, username: String) {
        viewModelScope.launch{
            repository.registrazione(email, password, username)
        }

    }

    fun verificaUnicitaCredenziali(email: String, username: String) {
        utenteRepo.unicitaUsername(username)
            .addOnSuccessListener { controllo_username ->
                if (controllo_username.isEmpty()) {
                    _userUnico.value = true
                } else {
                    _userUnico.value = false
                }
            }

        utenteRepo.unicitaEmail(email)
            .addOnSuccessListener { controllo_Email ->
                if (controllo_Email.isEmpty()) {
                    _emailUnica.value = true
                } else {
                    _emailUnica.value = false
                }
            }

    }

















}

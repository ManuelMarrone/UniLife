package com.example.unilife.ViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilife.Repository.RegistrazioneRepo
import kotlinx.coroutines.launch


class RegistrazioneViewModel : ViewModel() {




    private var _isUnico = MutableLiveData<Boolean>()
    val isUnico: LiveData<Boolean> get() = _isUnico
    private val repository = RegistrazioneRepo()


    fun registraUtente(email: String, password: String, username: String) {
        viewModelScope.launch{
            repository.registrazione(email, password, username)
        }

    }


    fun verificaUnicitaCredenziali(email: String, username: String) {


                repository.controlloUsername(username).addOnCompleteListener{
                    controllo_username->
                    repository.controlloEmail(email).addOnCompleteListener { controllo_email ->
                        if (controllo_username.result?.isEmpty == true && controllo_email.result?.isEmpty == true){
                            _isUnico.value = true
                        }
                    }
                }
            }
    }



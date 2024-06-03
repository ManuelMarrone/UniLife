package com.example.unilife.ViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unilife.Repository.RegistrazioneRepo
import com.example.unilife.StateUI.StatoRegistrazioneUi
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class RegistrazioneViewModel : ViewModel() {




    private var _isUnico = MutableLiveData<Boolean>()
    val isUnico: LiveData<Boolean> get() = _isUnico
    private val repository = RegistrazioneRepo()
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()



    private val _uiState = MutableStateFlow(StatoRegistrazioneUi())
    val uiState: StateFlow<StatoRegistrazioneUi> = _uiState.asStateFlow()




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



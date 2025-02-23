package com.example.unilife.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unilife.Model.Gruppo
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.ArchivioRepo
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.UtenteRepo
import com.example.unilife.StateUI.AccountUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountViewModel:ViewModel() {
    //StateFlow per la gestione dello stato dell'account
    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()
    private var _utente = MutableLiveData<Utente>()
    val utente: LiveData<Utente> get() = _utente
    private var _isUnico = MutableLiveData<Boolean>()
    val isUnico: LiveData<Boolean> get() = _isUnico

    // Repository
    private val userRepository = UtenteRepo()
    private val gruppoRepo = GruppoRepo()
    private val archivioRepo = ArchivioRepo()

    /**
     * Metodo per il logout
     */
    fun logOut() {
        userRepository.logOut()
        _uiState.value = AccountUiState.logout()
    }

    fun eliminaAccount()
    {
        val username = _utente.value!!.username!!
        val idGruppo = _utente.value!!.id_gruppo
        if(idGruppo != null)
        {
            gruppoRepo.rimuoviPartecipante(username,idGruppo).addOnFailureListener{
                Log.d("Rimozione partecipanti", "errore nella rimozione del partecipante")
            }

            gruppoRepo.getGruppo(idGruppo).addSnapshotListener { gruppo, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                val partecipantiGruppo = gruppo?.toObject(Gruppo::class.java)?.partecipanti as ArrayList<String>?
                if (partecipantiGruppo != null) {
                    if (partecipantiGruppo.isEmpty()) {
                        archivioRepo.eliminaStorage(idGruppo)
                        archivioRepo.eliminaRaccolta(idGruppo)
                        gruppoRepo.eliminaPagamenti(idGruppo)
                        gruppoRepo.eliminaAttivita(idGruppo)
                        gruppoRepo.eliminaGruppo(idGruppo).addOnFailureListener {e->
                            Log.e("Rimozione gruppo", "error ${e}")

                        }

                    }
                }

            }

        }
        userRepository.eliminaUtenteFireStore().addOnFailureListener{
            Log.d("Rimozione utente", "eliminazione utente fallita")
        }
        userRepository.eliminaUtenteAuth().addOnFailureListener{
            Log.d("Rimozione utente", "eliminazione utente fallita")
        }

    }

    fun modificaUtente(pwd:String, user:String) {


        userRepository.getUtente()?.addOnSuccessListener { utente ->
            val username = utente.toObject(Utente::class.java)!!.username.toString()
            val idGruppo = utente.toObject(Utente::class.java)?.id_gruppo
            if (idGruppo != null) {
                gruppoRepo.aggiornaPartecipante(idGruppo, user, username)
            }

        }

        userRepository.aggiornaUsername(user)
            ?.addOnFailureListener { e ->
                Log.e("modifica", "errore nella modifica dell'username${e.message}")
            }

        userRepository.aggiornaPassword(pwd)
            ?.addOnFailureListener { e ->
                Log.e("modifica", "errore nella modifica della password${e.message}")
            }

        userRepository.aggiornaPasswordFireStore(pwd)
            .addOnFailureListener { e ->
                Log.e("modifica", "errore nella modifica della password in firestore${e.message}")
            }
    }

    fun unicitaUsername(user:String) {
        userRepository.unicitaUsername(user)
            .addOnSuccessListener{ controllo_username->
                if (controllo_username.isEmpty()) {
                    _isUnico.value = true
                } else {
                    _isUnico.value = false
                }
            }
    }

    fun getUtente()
    {
        userRepository.getUtente()?.addOnSuccessListener { user->
            _utente.value = user?.toObject(Utente::class.java)
        }
    }
}
package com.example.unilife.ViewModel

<<<<<<< HEAD
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unilife.Model.Gruppo
import com.example.unilife.Model.Utente
import com.example.unilife.Repository.GruppoRepo
import com.example.unilife.Repository.UtenteRepo
import com.example.unilife.StateUI.AccountUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class AccountViewModel:ViewModel() {
    // StateFlow per la gestione dello stato dell'account
    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()
    private var _utente = MutableLiveData<Utente>()
    val utente: LiveData<Utente> get() = _utente
    private var _isUnico = MutableLiveData<Boolean>()
    val isUnico: LiveData<Boolean> get() = _isUnico
=======
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.unilife.Utils.InputCorretto
import com.example.unilife.View.Activity.AccessoActivity
import com.example.unilife.View.Activity.MainActivity
import com.example.unilife.ViewModel.AccountViewModel
import com.example.unilife.databinding.FragmentAccountBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
    private lateinit var viewBinding: FragmentAccountBinding
    private val viewModel: AccountViewModel by viewModels()
    private val inputCorretto = InputCorretto()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentAccountBinding.inflate(inflater, container, false)
        return viewBinding.root
>>>>>>> parent of 1861c46 (rotto)

    // Repository
    private val userRepository = UtenteRepo()
    private val gruppoRepo = GruppoRepo()

    /**
     * Metodo per il logout
     */
    fun logOut() {
        userRepository.logOut()
        _uiState.value = AccountUiState.logout()
    }

<<<<<<< HEAD
    fun eliminaAccount()
=======
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        lifecycleScope.launch {
            viewModel.uiState.collect {
                if (it.isLoggedOut) {
                    startActivity(
                        Intent(
                            requireActivity(),
                            AccessoActivity::class.java
                        )
                    )
                }
            }
        }

        setUI()

        viewBinding.userText.setOnClickListener{modificaDati()}
        viewBinding.passwordText.setOnClickListener{modificaDati()}

        viewBinding.salvaButton.setOnClickListener{onSalvaClick()}

        //logout account
        viewBinding.esciAccountButton.setOnClickListener {
            viewModel.logOut()
        }

        viewBinding.eliminaButton.setOnClickListener{onEliminaClick()}

    }

    private fun onEliminaClick()
>>>>>>> parent of 1861c46 (rotto)
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

                val partecipantiGruppo = gruppo!!.toObject(Gruppo::class.java)?.partecipanti as ArrayList<String>
                if (partecipantiGruppo.isEmpty())
                {
                    gruppoRepo.eliminaGruppo(idGruppo).addOnFailureListener{
                        Log.d("Rimozione partecipanti", "eliminazione gruppo fallita")
                    }
                }

            }

        }
        userRepository.eliminaUtenteFireStore().addOnSuccessListener{
            Log.d("Rimozione utente", "eliminazione utente fallita")
        }
        userRepository.eliminaUtenteAuth().addOnFailureListener{
            Log.d("Rimozione utente", "eliminazione utente fallita")
        }

    }

    fun modificaUtente(pwd:String, user:String){

        userRepository.aggiornaUsername(user)
            .addOnFailureListener { e ->
                Log.e("modifica", "errore nella modifica dell'username${e.message}")
            }

        userRepository.aggiornaPassword(pwd)
            .addOnFailureListener { e ->
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
        userRepository.getUtente().addOnSuccessListener { user->
            _utente.value = user?.toObject(Utente::class.java)
        }
    }
<<<<<<< HEAD
=======

    private fun modificaDati()
    {
        viewBinding.salvaButton.visibility = View.VISIBLE
    }

    companion object {

        fun newInstance() = AccountFragment()
    }


>>>>>>> parent of 1861c46 (rotto)
}
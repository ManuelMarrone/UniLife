package com.example.unilife.View.Fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.unilife.Repository.UtenteRepo
import com.example.unilife.View.Activity.AccessoActivity
import com.example.unilife.ViewModel.AccountViewModel
import com.example.unilife.databinding.FragmentAccountBinding
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
    private lateinit var viewBinding: FragmentAccountBinding
    private val viewModel: AccountViewModel by viewModels()
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val repo: UtenteRepo by lazy { UtenteRepo() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentAccountBinding.inflate(inflater, container, false)
        return viewBinding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.editTextText2.addTextChangedListener(textWatcher)
        viewBinding.editTextTextEmailAddress.addTextChangedListener(textWatcher)
        viewBinding.editTextTextPassword.addTextChangedListener(textWatcher)

        viewBinding.button2.setOnClickListener {
            // Aggiungi qui la logica per salvare i dati solo se le caselle di testo non sono vuote
            // Puoi anche aggiungere ulteriori controlli, ad esempio per verificare la validità delle informazioni inserite
            saveUserData()
        }

        checkFieldsAndSetButtonState()

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

        //logout account
        viewBinding.esciAccountButton.setOnClickListener {
            viewModel.logOut()
        }

    }
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            checkFieldsAndSetButtonState()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun checkFieldsAndSetButtonState() {
        val areAllFieldsEmpty = viewBinding.editTextText2.text.isEmpty() &&
                viewBinding.editTextTextEmailAddress.text.isEmpty() &&
                viewBinding.editTextTextPassword.text.isEmpty()

        viewBinding.button2.isEnabled = !areAllFieldsEmpty
    }


    private fun saveUserData() {
        val newUsername = viewBinding.editTextText2.text.toString()
        val newPassword = viewBinding.editTextTextPassword.text.toString()
        val newEmail = viewBinding.editTextTextEmailAddress.text.toString()

        val task = repo.getUtente()
        val documentSnapshot = Tasks.await(task) // Attendere il completamento del task

        val userId = documentSnapshot.getString("userId")
        if(userId != null) {
            if (newUsername.isNotEmpty()) {
                viewModel.isUsernameUnique(newUsername, {
                    viewModel.updateUsername(newUsername)
                }, {
                    Log.e("UsernameError", "Username already exists")
                })
            }

            if (newPassword.isNotEmpty()) {
                viewModel.updatePassword(newPassword)
                viewModel.updatePasswordInFirestore(userId, newPassword)
            }

            if (newEmail.isNotEmpty()) {
                viewModel.updateEmail(newEmail)
                viewModel.updateEmailInFirestore(userId, newEmail)
            }
        }else{
            Toast.makeText(requireContext(), "Errore: ID utente non trovato", Toast.LENGTH_SHORT).show()
        }

    }


    companion object {

        fun newInstance() = AccountFragment()
    }


}
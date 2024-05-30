package com.example.unilife.View.Fragment

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


    }

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
    {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Eliminazione Account")
            setMessage("Sei sicuro di voler eliminare il tuo account? Questa azione è irreversibile.")
            setPositiveButton("Elimina") { dialog, which ->
                viewModel.eliminaAccount()
                viewModel.logOut()
            }
            setNegativeButton("Annulla") { dialog, which ->
                // Chiudi il dialogo
                dialog.dismiss()
            }
            create()
            show()
        }
    }


    private fun setUI()
    {
        viewBinding.salvaButton.visibility = View.GONE
        viewModel.getUtente()

        viewModel.utente.observe(viewLifecycleOwner) { utente ->
            val username: String = utente.username!!
            viewBinding.userText.setText(username)
            val email: String = utente.email!!
            viewBinding.emailText.setText(email)
            viewBinding.emailText.isEnabled = false
            val password: String = utente.password!!
            viewBinding.passwordText.setText(password)
        }


    }

    private fun onSalvaClick()
    {
        val newUsername = viewBinding.userText.text.toString()
        val newPassword = viewBinding.passwordText.text.toString()

        when {
            !inputCorretto.isValidUsername(newUsername) -> viewBinding.userText.error=
                "Inserisci l'username. Non immettere degli spazi."


            !inputCorretto.isValidPassword(newPassword) -> viewBinding.passwordText.error =
                "Inserisci una password di almeno 6 caratteri"

            else -> {
                viewModel.unicitaUsername( newUsername)

                viewModel.isUnico.observe(viewLifecycleOwner){unico ->
                    if (unico) {
                        viewModel.modificaUtente( newPassword, newUsername)
                        viewModel.logOut()
                    } else {
                        Snackbar.make(
                            viewBinding.root,
                            "trova un nuovo username",
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                }

            }
        }
    }

    private fun modificaDati()
    {
        viewBinding.salvaButton.visibility = View.VISIBLE
    }

    companion object {

        fun newInstance() = AccountFragment()
    }


}
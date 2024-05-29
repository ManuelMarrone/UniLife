package com.example.unilife.View.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
<<<<<<< HEAD
import com.example.unilife.Utils.InputCorretto
import com.example.unilife.View.Activity.AccessoActivity
import com.example.unilife.ViewModel.AccountViewModel
import com.example.unilife.databinding.FragmentAccountBinding
import com.google.android.material.snackbar.Snackbar
=======
import androidx.lifecycle.lifecycleScope
import com.example.unilife.View.Activity.AccessoActivity
import com.example.unilife.View.Activity.ModificaActivity
import com.example.unilife.ViewModel.AccountViewModel
import com.example.unilife.databinding.FragmentAccountBinding
import kotlinx.coroutines.launch
>>>>>>> parent of 3e92c37 (gestione account)

class AccountFragment : Fragment() {
    private lateinit var viewBinding: FragmentAccountBinding
    private val viewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentAccountBinding.inflate(inflater, container, false)
        return viewBinding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.logOut.observe(viewLifecycleOwner) { isLoggedOut ->
            if (isLoggedOut) {
                startActivity(
                    Intent(
                        requireActivity(),
                        AccessoActivity::class.java
                    )
                )
            }
        }

<<<<<<< HEAD

        setUI()

        viewBinding.userText.setOnClickListener{modificaDati()}
        viewBinding.passwordText.setOnClickListener{modificaDati()}

        viewBinding.salvaButton.setOnClickListener{onSalvaClick()}

=======
>>>>>>> parent of 3e92c37 (gestione account)
        //logout account
        viewBinding.esciAccountButton.setOnClickListener {
            viewModel.logOut()
        }

        viewBinding.ChangeUsername.setOnClickListener {
            // Chiamata alla funzione per navigare alla ModificaActivity
            navigateToModificaActivity()
        }
    }

    private fun navigateToModificaActivity() {
        // Crea un intent per l'attività ModificaActivity
        val intent = Intent(requireActivity(), ModificaActivity::class.java)
        // Avvia l'attività ModificaActivity
        startActivity(intent)
    }

    companion object {
        fun newInstance() = AccountFragment()
    }


}
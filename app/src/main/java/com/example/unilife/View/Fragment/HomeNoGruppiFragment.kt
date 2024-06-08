package com.example.unilife.View.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.example.unilife.R
import com.example.unilife.ViewModel.HomeNoGruppiViewModel
import com.example.unilife.databinding.FragmentHomeNoGruppiBinding

class HomeNoGruppiFragment : Fragment() {
    private lateinit var viewBinding: FragmentHomeNoGruppiBinding
    private val viewModel: HomeNoGruppiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentHomeNoGruppiBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.accountButton.setOnClickListener(goToAccount())
        viewBinding.confermaButton.setOnClickListener{accettaInvito()}

    }

    companion object {
        fun newInstance() = HomeNoGruppiFragment()
    }

    private fun goToAccount(): View.OnClickListener {
        return View.OnClickListener {
            replaceFragment(AccountFragment.newInstance())
        }
    }

    private fun accettaInvito()
    {
        val codice = viewBinding.editTextNumberCodice.text.toString()

        viewModel.validaCodice(codice)

        //osserva se il codice è valido
        viewModel.isValid.observe(viewLifecycleOwner){isValid ->
            if (isValid == true) {
                //codice valido, l'account dev'essere aggiunto al gruppo
                viewModel.aggiungiUtenteGruppo(codice)
                viewBinding.editTextNumberCodice.setText("")
                viewBinding.editTextNumberCodice.clearFocus()
            } else {
                viewBinding.editTextNumberCodice.setError("Il codice non è corretto")
            }
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        // Ottieni il FragmentManager della tua Activity
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager

        // Inizia una transazione per sostituire il fragment
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)

        // Esegui la transazione
        fragmentTransaction.commit()

    }

}
package com.example.unilife.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.unilife.R
import com.example.unilife.StateUI.StatoRegistrazioneUi
import com.example.unilife.ViewModel.InvitaViewModel
import com.example.unilife.ViewModel.RegistrazioneViewModel
import com.example.unilife.databinding.ActivityRegistrazioneBinding
import com.example.unilife.databinding.FragmentHomeBinding
import com.example.unilife.databinding.FragmentInvitaBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InvitaFragment : Fragment() {

    private lateinit var binding: FragmentInvitaBinding
    private val viewModel: InvitaViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInvitaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.invitaBtn.setOnClickListener{invita()}
    }



    companion object {

        fun newInstance() = InvitaFragment()
    }


    private fun invita() {
        //in base allo stateFlow capisco se fa parte di un gruppo o meno
        //se fa parte di un gruppo devo solo inviare l'email
        //se non fa parte di un gruppo devo anche crearlo
        //viewModel.creaGruppo()
        //viewModel.invita()
    }
}
package com.example.unilife.View

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.unilife.Adapter.ListaPartecipantiAdapter
import com.example.unilife.Adapter.ListaSpesaAdapter
import com.example.unilife.Adapter.RecyclerViewItemClickListener
import com.example.unilife.Model.Utente
import com.example.unilife.R
import com.example.unilife.StateUI.StatoRegistrazioneUi
import com.example.unilife.Utils.InputCorretto
import com.example.unilife.ViewModel.InvitaViewModel
import com.example.unilife.ViewModel.RegistrazioneViewModel
import com.example.unilife.databinding.ActivityRegistrazioneBinding
import com.example.unilife.databinding.FragmentHomeBinding
import com.example.unilife.databinding.FragmentInvitaBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
/**
manca il controllo se il partecipante da eliminare è coinvolto in qualche attività del gruppo
 nessuna idea di come fare
**/

class InvitaFragment : Fragment(), RecyclerViewItemClickListener {

    private lateinit var binding: FragmentInvitaBinding
    private val viewModel: InvitaViewModel by viewModels()

    private val inputCorretto = InputCorretto()

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter : ListaPartecipantiAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInvitaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.invitaBtn.setOnClickListener { onClickInvita() }
        binding.creaBtn.setOnClickListener { onClickCrea() }

        recyclerView = binding.recyclerViewPartecipanti
        recyclerView.setLayoutManager(LinearLayoutManager(requireContext()))

        viewModel.partecipanti.observe(viewLifecycleOwner){listaUpdated ->
            recyclerView.adapter = ListaPartecipantiAdapter(requireContext(),this ,listaUpdated)
        }

        viewModel.idGruppo.observe(viewLifecycleOwner){idGruppo->
            aggiornaTasto(idGruppo)
        }

        viewModel.emailIntent.observe(viewLifecycleOwner, Observer { intent ->
            intent?.let {
                startActivity(Intent.createChooser(intent, "Scegli l'app per mandare l'email"))
                binding.destEmail.setText("")
                binding.destEmail.clearFocus()
            }
        })
    }


    companion object {
        fun newInstance() = InvitaFragment()
    }

    private fun aggiornaTasto(id:String?)
    {
        if (id != null) {
            binding.creaBtn.visibility = View.GONE
            binding.destEmail.visibility =View.VISIBLE
            binding.invitaBtn.visibility = View.VISIBLE
        }
        else
        {
            binding.destEmail.visibility =View.GONE
            binding.creaBtn.visibility = View.VISIBLE
            binding.invitaBtn.visibility = View.GONE
        }
    }

    private fun onClickInvita() {
            val destinatario = binding.destEmail.text.toString()

            if (inputCorretto.isValidEmail(destinatario)) {
                viewModel.invita(destinatario)
            }
            else {
                binding.destEmail.setError("Inserisci un'email valida")
            }

    }

    private fun onClickCrea() {
        Log.d("crea gruppo inf", "onclick")
        viewModel.creaGruppo()
    }

    override fun onItemClick(position: Int) {
        Log.d("Rimozione partecipanti", "posizione${position}")
        viewModel.rimuoviPartecipante(position)
    }
}
package com.example.unilife.View.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.Utils.InputCorretto
import com.example.unilife.View.Adapter.ListaPartecipantiAdapter
import com.example.unilife.View.Adapter.RecyclerViewButtonClickListener
import com.example.unilife.ViewModel.InvitaViewModel
import com.example.unilife.databinding.FragmentInvitaBinding


class InvitaFragment : Fragment(), RecyclerViewButtonClickListener<Int> {

    private lateinit var binding: FragmentInvitaBinding
    private val viewModel: InvitaViewModel by viewModels()

    private val inputCorretto = InputCorretto()

    private lateinit var recyclerView: RecyclerView

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
            recyclerView.adapter = ListaPartecipantiAdapter(requireContext(), this, listaUpdated)
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
            binding.destEmail.visibility = View.VISIBLE
            binding.invitaBtn.visibility = View.VISIBLE
        }
        else
        {
            binding.destEmail.visibility = View.GONE
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

    override fun onButtonClick(position: Int) {
        Log.d("Rimozione partecipanti", "posizione${position}")
        viewModel.rimuoviPartecipante(position)
    }
}
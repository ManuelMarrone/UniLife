package com.example.unilife.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.Adapter.ContattiAdapter
import com.example.unilife.Adapter.RecyclerViewButtonClickListener
import com.example.unilife.Utils.InputCorretto
import com.example.unilife.ViewModel.ContattiViewModel
import com.example.unilife.databinding.FragmentContattiBinding

class ContattiFragment : Fragment(), RecyclerViewButtonClickListener<String> {

    private lateinit var viewBinding: FragmentContattiBinding

    private lateinit var recyclerView: RecyclerView
    private val viewModel: ContattiViewModel by viewModels()

    private val inputCorretto = InputCorretto()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentContattiBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.addContattoButton.setOnClickListener{aggiungiContatto()}

        recyclerView = viewBinding.contattiRV
        recyclerView.setLayoutManager(LinearLayoutManager(requireContext()))

        viewModel.contatti.observe(viewLifecycleOwner){contatti ->
            recyclerView.adapter = ContattiAdapter(this ,contatti)
        }
    }

    companion object {
        fun newInstance() = ContattiFragment()
    }

    fun aggiungiContatto()
    {
        val nomeContatto = viewBinding.contattoNome.text.toString()
        val numTelefono = viewBinding.numTelefono.text.toString()
        //controlla se sono riempiti e se il numero di telefono è a 10 cifre

        if (inputCorretto.isValidPhone(numTelefono) ) {
            if(nomeContatto.isNotEmpty()) {
                viewModel.aggiungiContatto(nomeContatto, numTelefono)
                viewBinding.contattoNome.setText("")
                viewBinding.numTelefono.setText("")
            }
            else
            {
                viewBinding.contattoNome.setError("Non lasciare vuoto il campo")
            }
        }
        else {
            viewBinding.numTelefono.setError("Numero di telefono di 10 cifre")
        }

    }

    override fun onButtonClick(chiave: String) {
        viewModel.rimuoviContatto(chiave)
    }

}
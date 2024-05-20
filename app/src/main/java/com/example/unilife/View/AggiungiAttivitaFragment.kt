package com.example.unilife.View

import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup.Input
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.Adapter.ListaPartecipantiAdapter
import com.example.unilife.Adapter.ListaSpesaAdapter
import com.example.unilife.Adapter.PartecipantiAttivitaAdapter
import com.example.unilife.Adapter.RecyclerViewItemClickListener
import com.example.unilife.R
import com.example.unilife.Utils.InputCorretto
import com.example.unilife.ViewModel.AggiungiAttivitaViewModel
import com.example.unilife.ViewModel.HomeViewModel
import com.example.unilife.databinding.FragmentAggiungiAttivitaBinding
import com.example.unilife.databinding.FragmentVisualizzaSpesaBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class AggiungiAttivitaFragment : Fragment(), RecyclerViewItemClickListener<String> {

    private lateinit var viewBinding: FragmentAggiungiAttivitaBinding

    private lateinit var recyclerView: RecyclerView
    private val viewModel: AggiungiAttivitaViewModel by viewModels()
    private val inputCorretto = InputCorretto()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAggiungiAttivitaBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = viewBinding.RVPartecipantiAtt
        recyclerView.setLayoutManager(LinearLayoutManager(requireContext()))

        viewModel.partecipanti.observe(viewLifecycleOwner){mapUpdated ->
            val listaUpdated: ArrayList<String> = ArrayList()
            // Aggiungi tutte le chiavi della mappa all'ArrayList
            listaUpdated.addAll(mapUpdated.keys)
            recyclerView.adapter = PartecipantiAttivitaAdapter(requireContext(), this ,listaUpdated)
        }

        viewBinding.aggiungiButton.setOnClickListener{aggiungiAttivita()}
        viewBinding.annulaBtn.setOnClickListener{annulla()}

    }

    companion object {
        fun newInstance() = AggiungiAttivitaFragment()
    }

    override fun onItemClick(username: String) {
        viewModel.setChecked(username)
    }
    private fun annulla()
    {
        startActivity(
            Intent(
                requireActivity(),
                MainActivity::class.java
            ))
    }

    private fun aggiungiAttivita()
    {
        val titolo = viewBinding.titoloTxt.text.toString()
        val dataTxt = viewBinding.dataTxt.text.toString()

        viewModel.isValid()

        viewModel.isValid.observe(viewLifecycleOwner) { validita ->
            Log.e("validita","is ${validita}")
            if(validita == true) {
                if (inputCorretto.isValidDate(dataTxt)) {
                    if (titolo.isNotEmpty()) {
                        viewModel.aggiungiAttivita(titolo, dataTxt)

                    } else {
                        viewBinding.titoloTxt.setError("Non lasciare vuoto il campo")
                    }
                } else {
                    viewBinding.dataTxt.setError("Inserisci una data esistente giorno/mese/anno")
                }
            }
            else{
                Toast.makeText(requireContext(),"Seleziona almeno un coinquilino", Toast.LENGTH_SHORT).show()
            }
        }
        startActivity(
            Intent(
                requireActivity(),
                MainActivity::class.java
            )
        )

    }



}
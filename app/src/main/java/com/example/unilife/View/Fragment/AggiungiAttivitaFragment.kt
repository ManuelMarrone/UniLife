package com.example.unilife.View.Fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.View.Adapter.PartecipantiAttivitaAdapter
import com.example.unilife.View.Adapter.RecyclerViewButtonClickListener
import com.example.unilife.View.Activity.MainActivity
import com.example.unilife.View.Activity.SpesaActivity
import com.example.unilife.ViewModel.AggiungiAttivitaViewModel
import com.example.unilife.databinding.FragmentAggiungiAttivitaBinding
import java.util.Calendar

class AggiungiAttivitaFragment : Fragment(), RecyclerViewButtonClickListener<String> {

    private lateinit var viewBinding: FragmentAggiungiAttivitaBinding

    private lateinit var recyclerView: RecyclerView
    private val viewModel: AggiungiAttivitaViewModel by viewModels()

    private var data :String = ""

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
            recyclerView.adapter = PartecipantiAttivitaAdapter(requireContext(), this, mapUpdated)
        }

        viewBinding.aggiungiButton.setOnClickListener{aggiungiAttivita()}
        viewBinding.annulaBtn.setOnClickListener{annulla()}




        val dataTxt = viewBinding.dataTxt

        dataTxt.setOnClickListener {

            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our edit text.
                    data = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    dataTxt.setText(data)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

    }

    companion object {
        fun newInstance() = AggiungiAttivitaFragment()
    }

    override fun onButtonClick(username: String) {
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

    private fun aggiungiAttivita() {

        viewModel.validaInput()

        //osserva se il codice è valido
        viewModel.isValid.observe(viewLifecycleOwner) { isInputValid ->

                if (isInputValid) {
                    val titolo = viewBinding.titoloTxt.text.toString()

                    if (data.isNotEmpty()) {
                        if (titolo.isNotEmpty()) {
                            viewModel.aggiungiAttivita(titolo, data)
                            startActivity(
                                Intent(
                                    requireActivity(),
                                    MainActivity::class.java
                                )
                            )

                        } else {
                            viewBinding.titoloTxt.setError("Non lasciare vuoto il campo")
                        }
                    } else {
                        viewBinding.dataTxt.setError("Inserisci una data")
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Seleziona almeno un coinquilino",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }



}
package com.example.unilife.View.Fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.View.Adapter.PartecipantiAttivitaAdapter
import com.example.unilife.View.Adapter.RecyclerViewButtonClickListener
import com.example.unilife.Model.Attivita
import com.example.unilife.R
import com.example.unilife.ViewModel.VisualizzaModificaAttivitaViewModel
import com.example.unilife.databinding.FragmentVisualizzaModificaAttivitaBinding
import java.util.Calendar

class VisualizzaModificaAttivitaFragment : Fragment(), RecyclerViewButtonClickListener<String> {

    private lateinit var viewBinding: FragmentVisualizzaModificaAttivitaBinding

    private val viewModel: VisualizzaModificaAttivitaViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var idAttivita:String
    private lateinit var partecipantiAttivita:Map<String, Boolean>

    private var data :String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentVisualizzaModificaAttivitaBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = viewBinding.recyclerViewPartecipantiAtt
        recyclerView.setLayoutManager(LinearLayoutManager(requireContext()))

        //aggiorna la lista osservabile di nomi con cui riempire la recyclerView
        viewModel.partecipanti.observe(viewLifecycleOwner){mapUpdated ->
            recyclerView.adapter = PartecipantiAttivitaAdapter(requireContext(), this, mapUpdated)
        }


        setUIVisualizzazione()

        viewBinding.completaButton.setOnClickListener{onCompletaClick()}
        viewBinding.modificaButton.setOnClickListener{setUIModifica()}
        viewBinding.salvabutton.setOnClickListener{onSalvaClick()}

        viewBinding.annulaBtn.setOnClickListener {
            //torna al fragment precedente
            parentFragmentManager.popBackStack("lista", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        val dataTxt = viewBinding.visualizzaDatatext

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


    override fun onButtonClick(username: String) {
        viewModel.setChecked(username)
    }

    private fun onCompletaClick()
    {
        viewModel.completaAttivita(idAttivita, partecipantiAttivita as MutableMap<String, Boolean>)
        parentFragmentManager.popBackStack("lista", FragmentManager.POP_BACK_STACK_INCLUSIVE)    }

    private fun setUIModifica()
    {
        viewBinding.completaButton.visibility = View.GONE
        viewBinding.salvabutton.visibility = View.VISIBLE
        viewBinding.titoloFragment.text = "Modifica Attività"
        viewBinding.visualizzaTitoloText.isEnabled = true
        viewBinding.visualizzaDatatext.isEnabled = true

        //disabilitare le CheckBox dopo che l'adapter è impostato
        recyclerView.viewTreeObserver.addOnGlobalLayoutListener {
            for (i in 0 until recyclerView.childCount) {
                val child = recyclerView.getChildAt(i)
                val checkBox: CheckBox = child.findViewById(R.id.checkBox)
                checkBox.isEnabled = true
            }
        }
    }

    private fun setUIVisualizzazione()
    {
        val bundle = arguments
        if (bundle != null) {
            val attivita: Attivita = bundle.getSerializable("ATTIVITA") as Attivita
            idAttivita = bundle.getSerializable("CHIAVE") as String

            Log.d("VisualizzaAttivita", "visualizza-modifica ${attivita} e ${idAttivita}")

            val titolo: String = attivita.titolo
            viewBinding.visualizzaTitoloText.setText(titolo, TextView.BufferType.EDITABLE)
            viewBinding.visualizzaTitoloText.isEnabled = false

            data = attivita.data
            viewBinding.visualizzaDatatext.setText(data, TextView.BufferType.EDITABLE)
            viewBinding.visualizzaDatatext.isEnabled = false

            partecipantiAttivita = attivita.partecipanti

            viewModel.setPartecipantiAttività(partecipantiAttivita)

            // Disabilitare le CheckBox dopo che l'adapter è impostato
            recyclerView.viewTreeObserver.addOnGlobalLayoutListener {
                for (i in 0 until recyclerView.childCount) {
                    val child = recyclerView.getChildAt(i)
                    val checkBox: CheckBox = child.findViewById(R.id.checkBox)
                    checkBox.isEnabled = false
                }
            }

            viewBinding.completaButton.visibility = View.VISIBLE
            viewBinding.salvabutton.visibility = View.GONE

            viewModel.isPartecipante(partecipantiAttivita)
            viewModel.isPartecipante.observe(viewLifecycleOwner) { isPartecipante ->

                if (isPartecipante == true) {
                    viewBinding.completaButton.isEnabled = true
                } else {
                    viewBinding.completaButton.isEnabled = false
                }
            }


        }


    }
    private fun onSalvaClick()
    {    //al click di salva modifica l'attività e la salva nel db con update
        viewModel.validaInput()

        //osserva se il codice è valido
        viewModel.isValid.observe(viewLifecycleOwner) { isInputValid ->

            if (isInputValid) {
                val titolo = viewBinding.visualizzaTitoloText.text.toString()

                if (data.isNotEmpty()) {
                    if (titolo.isNotEmpty()) {
                        viewModel.salvaModifica(idAttivita, titolo, data)
                        parentFragmentManager.popBackStack("lista",
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )

                    } else {
                        viewBinding.visualizzaTitoloText.setError("Non lasciare vuoto il campo")
                    }
                } else {
                    viewBinding.visualizzaDatatext.setError("Inserisci una data")
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


    companion object {
        fun newInstance() = VisualizzaModificaAttivitaFragment()
    }

}
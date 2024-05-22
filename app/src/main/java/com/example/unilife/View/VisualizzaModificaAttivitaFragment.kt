package com.example.unilife.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.Adapter.PartecipantiAttivitaAdapter
import com.example.unilife.Adapter.RecyclerViewButtonClickListener
import com.example.unilife.Model.Attivita
import com.example.unilife.Model.Utente
import com.example.unilife.R
import com.example.unilife.ViewModel.AggiungiAttivitaViewModel
import com.example.unilife.ViewModel.ListaAttivitaViewModel
import com.example.unilife.ViewModel.VisualizzaModificaAttivitaViewModel
import com.example.unilife.databinding.FragmentCalendarioBinding
import com.example.unilife.databinding.FragmentVisualizzaModificaAttivitaBinding
import com.example.unilife.databinding.FragmentVisualizzaSpesaBinding

class VisualizzaModificaAttivitaFragment : Fragment(), RecyclerViewButtonClickListener<String> {

    private lateinit var viewBinding: FragmentVisualizzaModificaAttivitaBinding

    private val viewModel: VisualizzaModificaAttivitaViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView


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

        setUIVisualizzazione()

        viewBinding.completaButton.setOnClickListener{onCompletaClick()}
        viewBinding.modificaButton.setOnClickListener{setUIModifica()}

        viewBinding.annulaBtn.setOnClickListener {
            // Torna al fragment precedente
            parentFragmentManager.popBackStack()
        }
    }

    private fun shouldHandleBackPress(): Boolean {
        // Logica per determinare se gestire il back press
        // Restituisci true se gestisci il back press, altrimenti false
        return true
    }



    private fun onCompletaClick()
    {
        viewModel.completaAttivita()
    }

    private fun setUIModifica()
    {
        viewBinding.completaButton.visibility =View.GONE
        viewBinding.salvabutton.visibility =View.VISIBLE
        viewBinding.titoloFragment.text = "Modifica Attività"
        viewBinding.visualizzaTitoloText.isEnabled = true
        viewBinding.visualizzaDatatext.isEnabled = true

        // Disabilitare le CheckBox dopo che l'adapter è impostato
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
            val attivitaList: ArrayList<Attivita>? = bundle.getSerializable("ATTIVITA") as? ArrayList<Attivita>
            val chiave: ArrayList<String>? = bundle.getSerializable("CHIAVE") as? ArrayList<String>

            if (attivitaList != null && attivitaList.isNotEmpty()) {
                val attivitaObj: Attivita = attivitaList[0]
                Log.d("VisualizzaAttivita", "visualizza-modifica ${attivitaList} e ${chiave}")

                val titolo: String = attivitaObj.titolo
                viewBinding.visualizzaTitoloText.setText(titolo, TextView.BufferType.EDITABLE)
                viewBinding.visualizzaTitoloText.isEnabled = false

                val data: String = attivitaObj.data
                viewBinding.visualizzaDatatext.setText(data, TextView.BufferType.EDITABLE)
                viewBinding.visualizzaDatatext.isEnabled = false

                recyclerView.adapter = PartecipantiAttivitaAdapter(requireContext(), this ,attivitaObj.partecipanti)
                // Disabilitare le CheckBox dopo che l'adapter è impostato
                recyclerView.viewTreeObserver.addOnGlobalLayoutListener {
                    for (i in 0 until recyclerView.childCount) {
                        val child = recyclerView.getChildAt(i)
                        val checkBox: CheckBox = child.findViewById(R.id.checkBox)
                        checkBox.isEnabled = false
                    }
                }
            }


        }
        viewBinding.completaButton.visibility =View.VISIBLE
        viewBinding.salvabutton.visibility =View.GONE

    }
    override fun onButtonClick(username: String) {
        //viewModel.setChecked(username)
    }


    companion object {
        fun newInstance() = VisualizzaModificaAttivitaFragment()
    }

    //funzione che aggiorna la lista osservabile di nomi con cui riempire la recyclerView

    //al click di salva modifica l'attività e la salva nel db con update


}

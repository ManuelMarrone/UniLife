package com.example.unilife.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.unilife.Model.Attivita
import com.example.unilife.Model.Utente
import com.example.unilife.R
import com.example.unilife.ViewModel.ListaAttivitaViewModel
import com.example.unilife.ViewModel.VisualizzaModificaAttivitaViewModel
import com.example.unilife.databinding.FragmentCalendarioBinding
import com.example.unilife.databinding.FragmentVisualizzaModificaAttivitaBinding
import com.example.unilife.databinding.FragmentVisualizzaSpesaBinding

class VisualizzaModificaAttivitaFragment : Fragment() {

    private lateinit var viewBinding: FragmentVisualizzaModificaAttivitaBinding

    private val viewModel: VisualizzaModificaAttivitaViewModel by viewModels()
    private val viewModelList: ListaAttivitaViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentVisualizzaModificaAttivitaBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            }
        }



    }

    companion object {
        fun newInstance() = VisualizzaModificaAttivitaFragment()
    }

    //funzione che aggiorna la lista osservabile di nomi con cui riempire la recyclerView

    //al click di salva modifica l'attività e la salva nel db con update

    //al click di completa mette a false la selezione dell'utente che l'ha cliccata
    //se tutti sono a false allora elimina l'attività automaticamente
}

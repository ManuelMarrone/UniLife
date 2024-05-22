package com.example.unilife.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.Adapter.ListaAttivitaAdapter
import com.example.unilife.Adapter.RecyclerViewButtonClickListener
import com.example.unilife.Adapter.RecyclerViewItemClickListener
import com.example.unilife.R
import com.example.unilife.ViewModel.ListaAttivitaViewModel
import com.example.unilife.databinding.FragmentListaAttivitaBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ListaAttivitaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaAttivitaFragment : Fragment(), RecyclerViewButtonClickListener<Int>,RecyclerViewItemClickListener {

    private lateinit var viewBinding: FragmentListaAttivitaBinding
    private val viewModel: ListaAttivitaViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentListaAttivitaBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = viewBinding.RVListaAttivita
        recyclerView.setLayoutManager(LinearLayoutManager(requireContext()))

        val data = arguments?.getString("DATA")
        viewBinding.dataText.text = data
        fetchAttivita(data!!)

        viewModel.listaAttivita.observe(viewLifecycleOwner){listaUpdated ->
            Log.d("listaAtivita" , "Recycler ${listaUpdated}")
            recyclerView.adapter = ListaAttivitaAdapter(requireContext(),this ,this, listaUpdated)
        }
    }

    companion object {
        fun newInstance() = ListaAttivitaFragment()

    }

    private fun fetchAttivita(data:String)
    {
        viewModel.getAttivitaByData(data)
    }

    override fun onButtonClick(position: Int) {
        Log.d("Rimozione partecipanti", "posizione${position}")
        viewModel.rimuoviAttivita(position)
    }

    override fun onItemClickListener(posizione: Int) {
        viewModel.visualizzaItem(posizione)
        viewModel.attivita.observe(viewLifecycleOwner) { attivita ->
            Log.d("VisualizzaAttivita", "AttivitainLista ${attivita.keys}")
            Log.d("VisualizzaAttivita", "AttivitainLista ${attivita.values}")
            val bundle = Bundle().apply {
                putSerializable("ATTIVITA", ArrayList(attivita.values)) // Converte l'HashMap in ArrayList e la mette nel Bundle
                // Aggiungi altri dati se necessario
                putSerializable("CHIAVE", ArrayList(attivita.keys))
            }
            val fragment = VisualizzaModificaAttivitaFragment.newInstance()
            fragment.arguments = bundle
            replaceFragment(fragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        // Ottieni il FragmentManager della tua Activity
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        // Inizia una transazione per sostituire il fragment
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView3, fragment)
        fragmentTransaction.addToBackStack(null) // Aggiungi la transazione allo stack per tornare indietro
        fragmentTransaction.commit()

    }
}
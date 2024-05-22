package com.example.unilife.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.Adapter.ListaAttivitaAdapter
import com.example.unilife.Adapter.RecyclerViewDeleteClickListener
import com.example.unilife.ViewModel.ListaAttivitaViewModel
import com.example.unilife.databinding.FragmentListaAttivitaBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ListaAttivitaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaAttivitaFragment : Fragment(), RecyclerViewDeleteClickListener<Int> {

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
            recyclerView.adapter = ListaAttivitaAdapter(requireContext(),this ,listaUpdated)
        }
    }

    companion object {
        fun newInstance() = ListaAttivitaFragment()

    }

    private fun fetchAttivita(data:String)
    {
        viewModel.getAttivitaByData(data)
    }

    override fun onDeleteClick(position: Int) {
        Log.d("Rimozione partecipanti", "posizione${position}")
        viewModel.rimuoviAttivita(position)
    }
}
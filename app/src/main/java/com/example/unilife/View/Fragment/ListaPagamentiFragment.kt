package com.example.unilife.View.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.View.Activity.SpesaActivity
import com.example.unilife.View.Adapter.ListaAttivitaAdapter
import com.example.unilife.View.Adapter.RecyclerViewButtonClickListener
import com.example.unilife.View.Adapter.RecyclerViewItemClickListener
import com.example.unilife.ViewModel.ListaPagamentiViewModel
import com.example.unilife.databinding.FragmentListaPagamentiBinding


class ListaPagamentiFragment : Fragment(), RecyclerViewButtonClickListener<Int>,
    RecyclerViewItemClickListener {
    private lateinit var viewBinding: FragmentListaPagamentiBinding
    private val viewModel: ListaPagamentiViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentListaPagamentiBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.addButton.setOnClickListener{goToAggiungiSpesa()}

        recyclerView = viewBinding.RVListaPagamenti
        recyclerView.setLayoutManager(LinearLayoutManager(requireContext()))

        viewModel.listaPagamenti.observe(viewLifecycleOwner){listaUpdated ->
            recyclerView.adapter = ListaAttivitaAdapter(requireContext(), this, this, listaUpdated)
        }
    }
    override fun onButtonClick(position: Int) {
        Log.d("Rimozione partecipanti", "posizione${position}")
        viewModel.rimuoviPagamento(position)
    }

    override fun onItemClickListener(posizione: Int) {
        viewModel.visualizzaItem(posizione)
        viewModel.pagamento.observe(viewLifecycleOwner) { pagamento ->
            val chiave = pagamento.keys.first()
            val pagamento = pagamento[chiave]


            val bundle = Bundle().apply {
                putSerializable("PAGAMENTO", pagamento) // Converte l'HashMap in ArrayList e la mette nel Bundle
                putSerializable("CHIAVE", chiave)
            }
            startActivity(
                Intent(
                    requireActivity(),
                    SpesaActivity::class.java
                )
                    .putExtra("FRAGMENT", "VisualizzaSpesaFragment")
                    .putExtras(bundle)
            )

        }
    }

    private fun goToAggiungiSpesa() {
            startActivity(
                Intent(
                    requireActivity(),
                    SpesaActivity::class.java
                )
                    .putExtra("FRAGMENT", "AggiungiSpesaFragment")
            )
    }

    companion object {
        fun newInstance() = ListaPagamentiFragment()
    }

}
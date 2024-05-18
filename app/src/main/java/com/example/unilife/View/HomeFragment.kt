package com.example.unilife.View

import android.content.Intent
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
import com.example.unilife.Adapter.ListaPartecipantiAdapter
import com.example.unilife.Adapter.ListaSpesaAdapter
import com.example.unilife.Adapter.RecyclerViewItemClickListener
import com.example.unilife.R
import com.example.unilife.ViewModel.HomeViewModel
import com.example.unilife.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), RecyclerViewItemClickListener {
    private lateinit var viewBinding: FragmentHomeBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var recyclerView: RecyclerView
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.accountButton.setOnClickListener(goToAccount())
        viewBinding.archivioButton.setOnClickListener(goToArchivio())
        viewBinding.aggiungiBtn.setOnClickListener{aggiungiElementoLista()}

        recyclerView = viewBinding.RVListaSpesa
        recyclerView.setLayoutManager(LinearLayoutManager(requireContext()))

        viewModel.listaSpesa.observe(viewLifecycleOwner){listaUpdated ->
            recyclerView.adapter = ListaSpesaAdapter(this ,listaUpdated)
        }
    }

    private fun goToAccount(): View.OnClickListener {
        return View.OnClickListener {
            replaceFragment(AccountFragment.newInstance())
        }
    }

    private fun goToArchivio(): View.OnClickListener {
        return View.OnClickListener {
            val intent = Intent(requireActivity(), ArchivioActivity::class.java)
            startActivity(intent)
        }
    }

    private fun aggiungiElementoLista()
    {
        val elemento = viewBinding.textElemento.text.toString()
        if (elemento.isNotEmpty()) {
            viewModel.aggiungiElementoListaSpesa(elemento)
            viewBinding.textElemento.setText("")
        }
        else {
            viewBinding.textElemento.setError("Inserisci un elemento da comprare")
        }

    }


    private fun replaceFragment(fragment: Fragment) {
        // Ottieni il FragmentManager della tua Activity
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager

        // Inizia una transazione per sostituire il fragment
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)

        // Esegui la transazione
        fragmentTransaction.commit()

    }

    override fun onItemClick(position: Int) {
        viewModel.rimuoviElemento(position)
    }



    companion object {
        fun newInstance() = HomeFragment()

    }
}
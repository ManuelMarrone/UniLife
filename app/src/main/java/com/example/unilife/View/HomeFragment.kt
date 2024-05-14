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
import com.example.unilife.Adapter.ListaPartecipantiAdapter
import com.example.unilife.Adapter.ListaSpesaAdapter
import com.example.unilife.Adapter.RecyclerViewItemClickListener
import com.example.unilife.R
import com.example.unilife.ViewModel.HomeViewModel
import com.example.unilife.databinding.FragmentHomeBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
    private lateinit var listaSpesa: ArrayList<String>
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

        getListaSpesa()

        recyclerView = viewBinding.RVListaSpesa
        recyclerView.setLayoutManager(LinearLayoutManager(requireContext()))
    }

    private fun goToAccount(): View.OnClickListener {
        return View.OnClickListener {
            replaceFragment(AccountFragment.newInstance())
        }
    }

    private fun goToArchivio(): View.OnClickListener {
        return View.OnClickListener {
            replaceFragment(ArchivioFragment.newInstance())
        }
    }

    private fun aggiungiElementoLista()
    {
        val elemento = viewBinding.textElemento.text.toString()
        if (elemento.isNotEmpty()) {
            viewModel.aggiungiElemento(elemento)
            viewBinding.textElemento.setText("")
//            viewModel.getListaSpesa().observe(this, Observer{
//                //Toast here
//            })
            getListaSpesa()
        }
        else {
            viewBinding.textElemento.setError("Inserisci un elemento da comprare")
        }

    }

    private fun getListaSpesa() {
        viewModel.getListaSpesa { lista ->
            if (lista != null) {
                listaSpesa = lista
                Log.d("listaSpesa", "part ${listaSpesa}")
                recyclerView.adapter = ListaSpesaAdapter(this ,listaSpesa)
            } else {
                listaSpesa = ArrayList()
                Log.d("listaSpesa", "null")
            }

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
        viewModel.rimuoviElemento(listaSpesa[position])
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        fun newInstance() = HomeFragment()

    }
}
package com.example.unilife.View.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.Model.Pagamento
import com.example.unilife.R
import com.example.unilife.View.Activity.MainActivity
import com.example.unilife.View.Adapter.PartecipantiCheckBoxAdapter
import com.example.unilife.ViewModel.VisualizzaSpesaViewModel
import com.example.unilife.databinding.FragmentVisualizzaSpesaBinding

class VisualizzaSpesaFragment : Fragment() {

    private lateinit var viewBinding: FragmentVisualizzaSpesaBinding

    private val viewModel: VisualizzaSpesaViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var pagamento: Pagamento
    private lateinit var idPagamento:String
    private lateinit var partecipantiPagamento:Map<String, Boolean>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentVisualizzaSpesaBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = viewBinding.RVPartecipantiPagamento
        recyclerView.setLayoutManager(LinearLayoutManager(requireContext()))

        //aggiorna la lista osservabile di nomi con cui riempire la recyclerView
        viewModel.partecipanti.observe(viewLifecycleOwner) { mapUpdated ->
            recyclerView.adapter = PartecipantiCheckBoxAdapter(requireContext(), null, mapUpdated)
        }

        setUIVisualizzazione()

        viewBinding.buttonPaga.setOnClickListener { onPagaClick() }
        viewBinding.modificaButton.setOnClickListener{onModificaClick()}
        viewBinding.buttonIndietro.setOnClickListener {
            //torna al fragment precedente
            startActivity(
                Intent(
                    requireActivity(),
                    MainActivity::class.java
                )
                    .putExtra("FRAGMENT_TO_LOAD", "ListaPagamentiFragment")
            )
        }
    }


    private fun onPagaClick()
    {
        viewModel.completaPagamento(idPagamento, partecipantiPagamento as MutableMap<String, Boolean>)
        startActivity(
            Intent(
                requireActivity(),
                MainActivity::class.java
            )
                .putExtra("FRAGMENT_TO_LOAD", "ListaPagamentiFragment")
        )
    }

    private fun onModificaClick()
    {
        val bundle = Bundle().apply {
            putString("OBIETTIVO", "Modifica")
            putSerializable("PAGAMENTO", pagamento)
            // Aggiungi altri dati se necessario
            putSerializable("CHIAVE", idPagamento)
        }
        val fragment = ModificaAggiungiSpesaFragment.newInstance()
        fragment.arguments = bundle
        replaceFragment(fragment)
    }

    private fun setUIVisualizzazione() {
        val bundle = arguments
        if (bundle != null) {
            pagamento = bundle.getSerializable("PAGAMENTO") as Pagamento
            idPagamento = bundle.getSerializable("CHIAVE") as String

            Log.d("VisualizzaPagamento", "visualizza ${pagamento} e ${idPagamento}")

            val titolo: String = pagamento.titolo
            viewBinding.textViewTitolo.setText(titolo)
            val denaro: Double = pagamento.denaro
            viewBinding.textViewPrezzo.setText(denaro.toString())
            val quota: Double = pagamento.quota
            viewBinding.textViewQuota.setText(quota.toString())


            partecipantiPagamento = pagamento.partecipanti

            viewModel.setPartecipantiPagamento(partecipantiPagamento)

            // Disabilitare le CheckBox dopo che l'adapter è impostato
            recyclerView.viewTreeObserver.addOnGlobalLayoutListener {
                for (i in 0 until recyclerView.childCount) {
                    val child = recyclerView.getChildAt(i)
                    val checkBox: CheckBox = child.findViewById(R.id.checkBox)
                    checkBox.isEnabled = false
                }
            }

            viewModel.isPartecipante(partecipantiPagamento)
            viewModel.isPartecipante.observe(viewLifecycleOwner) { isPartecipante ->

                if (isPartecipante == true) {
                    viewBinding.buttonPaga.isEnabled = true
                } else {
                    viewBinding.buttonPaga.isEnabled = false
                }
            }


        }
    }
    private fun replaceFragment(fragment: Fragment) {
        // Ottieni il FragmentManager della tua Activity
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        // Inizia una transazione per sostituire il fragment
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView2, fragment)
        fragmentTransaction.commit()

    }


    companion object {
        fun newInstance() = VisualizzaSpesaFragment()

    }
}
package com.example.unilife.View.Fragment

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
import com.example.unilife.Model.Pagamento
import com.example.unilife.View.Activity.MainActivity
import com.example.unilife.View.Adapter.PartecipantiCheckBoxAdapter
import com.example.unilife.View.Adapter.RecyclerViewButtonClickListener
import com.example.unilife.ViewModel.ModificaAggiungiSpesaViewModel
import com.example.unilife.databinding.FragmentModificaAggiungiSpesaBinding

class ModificaAggiungiSpesaFragment : Fragment() , RecyclerViewButtonClickListener<String> {

    private lateinit var viewBinding: FragmentModificaAggiungiSpesaBinding

    private lateinit var recyclerView: RecyclerView
    private val viewModel: ModificaAggiungiSpesaViewModel by viewModels()
    private lateinit var idPagamento:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentModificaAggiungiSpesaBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = viewBinding.RVpartecipantiPagamento
        recyclerView.setLayoutManager(LinearLayoutManager(requireContext()))

        viewModel.partecipanti.observe(viewLifecycleOwner) { mapUpdated ->
            recyclerView.adapter = PartecipantiCheckBoxAdapter(requireContext(), this, mapUpdated)
        }


        viewBinding.aggiungiButton.setOnClickListener { aggiungiPagamento() }
        viewBinding.annulaBtn.setOnClickListener { annulla() }

        viewBinding.buttonSalva.visibility = View.GONE
        viewBinding.aggiungiButton.visibility = View.VISIBLE
        setUIModifica()

        viewBinding.buttonSalva.setOnClickListener{onSalvaClick()}
    }

        companion object {
        fun newInstance() = ModificaAggiungiSpesaFragment()
    }

    private fun setUIModifica() {
        val bundle = arguments
        if (bundle != null) {
            val obiettivo: String = bundle.getString("OBIETTIVO").toString()
            if (obiettivo == "Modifica") {
                val pagamento: Pagamento = bundle.getSerializable("PAGAMENTO") as Pagamento
                idPagamento = bundle.getSerializable("CHIAVE") as String

                val titolo: String = pagamento.titolo
                viewBinding.titoloText.setText(titolo)
                val denaro: Double = pagamento.denaro
                viewBinding.soldiText.setText(denaro.toString())

                viewBinding.buttonSalva.visibility = View.VISIBLE
                viewBinding.aggiungiButton.visibility = View.GONE
                viewBinding.titFragment.text = "Modifica pagamento"

            }


        }
    }

    private fun onSalvaClick()
    {    //al click di salva modifica l'attività e la salva nel db con update
        viewModel.validaInput()
        viewModel.isValid.observe(viewLifecycleOwner) { isInputValid ->

            if (isInputValid) {
                val titolo = viewBinding.titoloText.text.toString()
                val denaro = viewBinding.soldiText.text.toString()

                if (denaro.isNotEmpty()) {
                    try {
                        //conversione della stringa in un valore Double
                        val soldi: Double = denaro.toDouble()

                        if (titolo.isNotEmpty()) {
                            viewModel.salvaModifica(idPagamento, titolo, soldi)
                            startActivity(
                                Intent(
                                    requireActivity(),
                                    MainActivity::class.java
                                )
                                    .putExtra("FRAGMENT_TO_LOAD", "ListaPagamentiFragment")
                            )
                        } else {
                            viewBinding.titoloText.setError("Non lasciare vuoto il campo")
                        }
                    } catch (e: NumberFormatException) {
                        viewBinding.soldiText.setError("Inserisci un valore numerico valido")
                    }
                } else {
                    viewBinding.soldiText.setError("Inserisci il denaro da dividere")
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

    override fun onButtonClick(username: String) {
        viewModel.setChecked(username)
    }
    private fun annulla()
    {
        startActivity(
            Intent(
                requireActivity(),
                MainActivity::class.java
            )
                .putExtra("FRAGMENT_TO_LOAD", "ListaPagamentiFragment")
        )
    }

    private fun aggiungiPagamento() {

        viewModel.validaInput()

        //osserva se il codice è valido
        viewModel.isValid.observe(viewLifecycleOwner) { isInputValid ->

            if (isInputValid) {
                val titolo = viewBinding.titoloText.text.toString()
                val denaro = viewBinding.soldiText.text.toString()

                if (denaro.isNotEmpty()) {
                    try {
                        //conversione della stringa in un valore Double
                        val soldi: Double = denaro.toDouble()

                        if (titolo.isNotEmpty()) {
                            viewModel.aggiungiPagamento(titolo, soldi)
                            startActivity(
                                Intent(
                                    requireActivity(),
                                    MainActivity::class.java
                                ).putExtra("FRAGMENT_TO_LOAD", "ListaPagamentiFragment")
                            )
                        } else {
                            viewBinding.titoloText.setError("Non lasciare vuoto il campo")
                        }
                    } catch (e: NumberFormatException) {
                        viewBinding.soldiText.setError("Inserisci un valore numerico valido")
                    }
                } else {
                    viewBinding.soldiText.setError("Inserisci il denaro da dividere")
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
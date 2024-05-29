package com.example.unilife.View.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import com.example.unilife.View.Activity.AttivitaActivity
import com.example.unilife.databinding.FragmentCalendarioBinding

class CalendarioFragment : Fragment() {

    private lateinit var viewBinding: FragmentCalendarioBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentCalendarioBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.calendarView
            .setOnDateChangeListener { _, year, month, dayOfMonth ->
                val data = (dayOfMonth.toString() + "-"
                        + (month + 1) + "-" + year)

                goToVisualizzaListaAttivita(data)
            }


        viewBinding.attivitaBtn.setOnClickListener{goToAggiungiAttivita()}
    }

    private fun goToAggiungiAttivita() {
            startActivity(
                Intent(
                    requireActivity(),
                    AttivitaActivity::class.java
                )
                    .putExtra("FRAGMENT_TO_LOAD", "AggiungiAttivitaFragment")
            )
    }

    private fun goToVisualizzaListaAttivita(data:String) {
            startActivity(
                Intent(
                    requireActivity(),
                    AttivitaActivity::class.java
                )
                    .putExtra("FRAGMENT_TO_LOAD", "ListaAttivitaFragment")
                    .putExtra("DATA", data)
            )
    }

    companion object {
        fun newInstance() = CalendarioFragment()
    }
}
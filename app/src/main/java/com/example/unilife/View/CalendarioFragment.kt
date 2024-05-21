package com.example.unilife.View

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import com.example.unilife.R
import com.example.unilife.databinding.FragmentCalendarioBinding
import com.example.unilife.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [CalendarioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
            .setOnDateChangeListener(
                CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
                    val data = (dayOfMonth.toString() + "-"
                            + (month + 1) + "-" + year)

                    goToVisualizzaListaAttivita(data)
                })


        viewBinding.attivitaBtn.setOnClickListener(goToAggiungiAttivita())
    }

    private fun goToAggiungiAttivita(): View.OnClickListener {
        return View.OnClickListener {
            startActivity(
                Intent(
                    requireActivity(),
                    AttivitaActivity::class.java
                )
                    .putExtra("FRAGMENT_TO_LOAD", "AggiungiAttivitaFragment")
            )
        }
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
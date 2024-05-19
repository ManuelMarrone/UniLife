package com.example.unilife.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import com.example.unilife.R
import com.example.unilife.databinding.FragmentCalendarioBinding
import com.example.unilife.databinding.FragmentVisualizzaModificaAttivitaBinding
import com.example.unilife.databinding.FragmentVisualizzaSpesaBinding

class VisualizzaModificaAttivitaFragment : Fragment() {

    private lateinit var viewBinding: FragmentVisualizzaModificaAttivitaBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentVisualizzaModificaAttivitaBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun newInstance() = VisualizzaModificaAttivitaFragment()
    }
}

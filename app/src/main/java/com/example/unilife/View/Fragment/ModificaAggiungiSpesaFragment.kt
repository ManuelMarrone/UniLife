package com.example.unilife.View.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unilife.R

class ModificaAggiungiSpesaFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modifica_aggiungi_spesa, container, false)
    }

    companion object {
        fun newInstance() = ModificaAggiungiSpesaFragment()
    }
}
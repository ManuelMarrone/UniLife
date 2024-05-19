package com.example.unilife.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.unilife.R
import com.example.unilife.databinding.FragmentAggiungiAttivitaBinding
import com.example.unilife.databinding.FragmentVisualizzaSpesaBinding


class AggiungiAttivitaFragment : Fragment() {

    private lateinit var viewBinding: FragmentAggiungiAttivitaBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAggiungiAttivitaBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun newInstance() = AggiungiAttivitaFragment()
    }
}
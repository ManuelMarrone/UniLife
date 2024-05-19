package com.example.unilife.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.unilife.R
import com.example.unilife.databinding.FragmentAggiungiAttivitaBinding
import com.example.unilife.databinding.FragmentListaAttivitaBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ListaAttivitaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaAttivitaFragment : Fragment() {

    private lateinit var viewBinding: FragmentListaAttivitaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentListaAttivitaBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = ListaAttivitaFragment()

    }
}
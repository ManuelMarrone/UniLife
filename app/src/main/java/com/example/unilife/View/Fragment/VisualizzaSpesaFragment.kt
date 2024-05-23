package com.example.unilife.View.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unilife.R

/**
 * A simple [Fragment] subclass.
 * Use the [VisualizzaSpesaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VisualizzaSpesaFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visualizza_spesa, container, false)
    }

    companion object {

    }
}
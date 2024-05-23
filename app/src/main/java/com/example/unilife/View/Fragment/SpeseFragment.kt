package com.example.unilife.View.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unilife.View.Activity.SpesaActivity
import com.example.unilife.databinding.FragmentSpeseBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SpeseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpeseFragment : Fragment() {
    private lateinit var viewBinding: FragmentSpeseBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSpeseBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.addButton.setOnClickListener(goToSpesa())
    }

    private fun goToSpesa(): View.OnClickListener {
        return View.OnClickListener {
            startActivity(
                Intent(
                    requireActivity(),
                    SpesaActivity::class.java
                )
            )
        }
    }

    companion object {
        fun newInstance() = SpeseFragment()
    }
}
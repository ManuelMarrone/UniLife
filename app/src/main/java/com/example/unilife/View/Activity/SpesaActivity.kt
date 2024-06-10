package com.example.unilife.View.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.unilife.R
import com.example.unilife.View.Fragment.ModificaAggiungiSpesaFragment
import com.example.unilife.View.Fragment.VisualizzaSpesaFragment
import com.example.unilife.databinding.ActivitySpesaBinding

class SpesaActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySpesaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_spesa)
        val fragmentToLoad = intent.getStringExtra("FRAGMENT")

        //recupera il Bundle dall'Intent
        val bundle = intent.extras

        //carica il Fragment appropriato
        when (fragmentToLoad) {
            "AggiungiSpesaFragment" ->replaceFragment(ModificaAggiungiSpesaFragment.newInstance())
            "VisualizzaSpesaFragment" -> {
                val fragment = VisualizzaSpesaFragment().apply {
                    arguments = bundle
                }
                replaceFragment(fragment)
            }
            else -> {finish()}
        }


    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = this.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView2, fragment)
        fragmentTransaction.commit()
    }
}
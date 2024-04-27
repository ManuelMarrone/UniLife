package com.example.unifile.View

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.unifile.R
import com.example.unifile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        replaceFragment(HomeFragment.newInstance())
        bottomNavigationListener()
    }

     //Metodo per gestire il comportamento della bottom navigation

    private fun bottomNavigationListener() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_home -> replaceFragment(HomeFragment.newInstance())
                R.id.bottom_phone -> replaceFragment(ContattiFragment.newInstance())
                R.id.bottom_add -> replaceFragment(InvitaFragment.newInstance())
                R.id.bottom_calendario -> replaceFragment(CalendarioFragment.newInstance())
                R.id.bottom_spese -> replaceFragment(SpeseFragment.newInstance())
                else -> {}
            }
            true
        }
    }


    //Metodo per sostituire il fragment attuale con quello passato come parametro

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment)
            commit()
        }
    }

}
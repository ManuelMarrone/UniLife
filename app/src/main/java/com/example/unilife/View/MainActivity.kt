package com.example.unilife.View

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.unilife.R
import com.example.unilife.ViewModel.AccessoViewModel
import com.example.unilife.ViewModel.MainViewModel
import com.example.unilife.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setHome()
    }


    //metodo che cambia il fragment in base a se l'utente fa parte di un gruppo o meno
    public fun setHome()
    {
        viewModel.getGruppo() //per assegnare il valore a idGruppo nel viewModel

        //osservatore
        val idGruppoObserver = Observer<String?> {idGruppo ->
            if (idGruppo != null) {
                replaceFragment(HomeFragment.newInstance())
                abilitaBottomNavigation()
                bottomNavigationListenerGruppi()
                Log.d("MyActivity", "ID del gruppo: $idGruppo")
            } else {
                replaceFragment(HomeNoGruppiFragment.newInstance())
                disabilitaBottomNavigation()
                bottomNavigationListenerNoGruppi()
                Log.d("MyActivity", "ID del gruppo non disponibile nel main")
            }
        }
        viewModel.idGruppoLiveData.observe(this, idGruppoObserver)


    }

    private fun disabilitaBottomNavigation(){
        binding.bottomNavigation.apply {
            menu.findItem(R.id.bottom_calendario).isVisible = false
            menu.findItem(R.id.bottom_phone).isVisible = false
            menu.findItem(R.id.bottom_spese).isVisible = false
        }
    }

    private fun abilitaBottomNavigation(){
        binding.bottomNavigation.apply {
            menu.findItem(R.id.bottom_calendario).isVisible = true
            menu.findItem(R.id.bottom_phone).isVisible = true
            menu.findItem(R.id.bottom_spese).isVisible = true
        }
    }

    private fun bottomNavigationListenerNoGruppi() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_home -> replaceFragment(HomeNoGruppiFragment.newInstance())
                R.id.bottom_add -> replaceFragment(InvitaFragment.newInstance())
                else -> {}
            }
            true
        }
    }

     //Metodo per gestire il comportamento della bottom navigation
    private fun bottomNavigationListenerGruppi() {
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
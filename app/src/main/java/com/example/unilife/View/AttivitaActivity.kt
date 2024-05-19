package com.example.unilife.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.unilife.R
import com.example.unilife.databinding.ActivityAttivitaBinding
import com.example.unilife.databinding.ActivityMainBinding

class AttivitaActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityAttivitaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_attivita)

        // Recupera l'Intent e i dati passati
        val fragmentToLoad = intent.getStringExtra("FRAGMENT_TO_LOAD")

        // Carica il Fragment appropriato
        when (fragmentToLoad) {
            "ListaAttivitaFragment" -> replaceFragment(ListaAttivitaFragment.newInstance())
            "AggiungiAttivitaFragment" -> replaceFragment(AggiungiAttivitaFragment.newInstance())
            else -> {ListaAttivitaFragment.newInstance()}
        }


    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView3, fragment)
            commit()
        }
    }
    }

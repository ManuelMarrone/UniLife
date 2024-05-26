package com.example.unilife.View.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.unilife.R
import com.example.unilife.View.Fragment.AggiungiAttivitaFragment
import com.example.unilife.View.Fragment.ListaAttivitaFragment
import com.example.unilife.databinding.ActivityAttivitaBinding

class AttivitaActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityAttivitaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_attivita)

        //recupera i dati passati dall'activity
        val fragmentToLoad = intent.getStringExtra("FRAGMENT_TO_LOAD")
        val data = intent.getStringExtra("DATA")

        //carica il Fragment appropriato
        when (fragmentToLoad) {
            "ListaAttivitaFragment" ->{
                val bundle = Bundle()
                bundle.putString("DATA", data)
                val listaAttivitaFragment = ListaAttivitaFragment()
                listaAttivitaFragment.arguments = bundle
                replaceFragment(listaAttivitaFragment)
            }
            "AggiungiAttivitaFragment" -> replaceFragment(AggiungiAttivitaFragment.newInstance())
            else -> {finish()}
        }


    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView3, fragment)
            commit()
        }
    }
}
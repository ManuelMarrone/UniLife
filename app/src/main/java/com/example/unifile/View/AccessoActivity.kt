package com.example.unifile.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.unifile.R
import com.example.unifile.databinding.ActivityAccessoBinding

class AccessoActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityAccessoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_accesso)

        viewBinding.creaButton.setOnClickListener(registraClick())
        viewBinding.accediButton.setOnClickListener(accediClick())
    }


    /** Al click del pulsante accedi vengono controllate le credenziali
      e se corrette viene effettuata l'accesso*/
    private fun accediClick(): View.OnClickListener {
        return View.OnClickListener {
            //va fatto tutto il controllo sulle crendenziali
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
        }
    }

    //apertura activity registrazione
    private fun registraClick(): View.OnClickListener {
        return View.OnClickListener {
            startActivity(
                Intent(
                    this,
                    RegistrazioneActivity::class.java
                )
            )
        }
    }
}
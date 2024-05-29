package com.example.unilife.View.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.unilife.databinding.ActivityAccessoBinding
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import androidx.activity.viewModels
import com.example.unilife.Utils.InputCorretto
import com.example.unilife.ViewModel.AccessoViewModel


class AccessoActivity : AppCompatActivity() {

    companion object {
        private const val ERRORE_EMAIL = "Inserisci un'e-mail valida"
        private const val ERRORE_PASSWORD = "Inserisci una password"
    }

    private lateinit var binding: ActivityAccessoBinding
    private val viewModel: AccessoViewModel by viewModels()
    private val inputCorretto = InputCorretto()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccessoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.accediButton.setOnClickListener { onclickaccedi() }
        binding.creaButton.setOnClickListener { creaClick() }

    }


    /** Al click del pulsante accedi vengono controllate le credenziali
    e se corrette viene effettuato l'accesso*/

    private fun onclickaccedi() {
        val email = binding.editTextEmailLogin.text.toString()
        val password = binding.editTextPasswordLogin.text.toString()

       if (inputCorretto.isValidEmail(email)) {
            if (inputCorretto.isValidPassword(password)) {
                viewModel.accedi(email, password)
                startActivity(Intent(this@AccessoActivity, MainActivity::class.java))
            } else {
                binding.editTextPasswordLogin.error = ERRORE_PASSWORD
                Toast.makeText(this, "pass Failed", Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.editTextEmailLogin.error = ERRORE_EMAIL
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()

        }


    }

/**apertura activity registrazione*/
    private fun creaClick(){

        startActivity(Intent(this, RegistrazioneActivity::class.java))
        finish()
    }
    override fun onStart() {
        super.onStart()
        if (viewModel.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}






































    /** private fun accediClick(email: String, password: String): View.OnClickListener {
        return View.OnClickListener {

            //va fatto tutto il controllo sulle crendenziali
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
        }
    }*/






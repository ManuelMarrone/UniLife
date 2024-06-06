package com.example.unilife.View.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.unilife.Utils.InputCorretto
import com.example.unilife.ViewModel.AccessoViewModel
import com.example.unilife.databinding.ActivityAccessoBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


class AccessoActivity : AppCompatActivity() {

    companion object {
        private const val ERRORE_EMAIL = "Inserisci un'e-mail valida"
        private const val ERRORE_PASSWORD = "Inserisci una password"
    }

    private lateinit var binding: ActivityAccessoBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val viewModel: AccessoViewModel by viewModels()
    private val inputCorretto = InputCorretto()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccessoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()
        binding.accediButton.setOnClickListener { onclickaccedi() }
        binding.creaButton.setOnClickListener { creaClick() }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->

                if (state.isLoggedIn) {
                    startActivity(Intent(this@AccessoActivity, MainActivity::class.java))
                    finish()
                }
                if (state.error != null) {

                    Toast.makeText(
                        this@AccessoActivity,
                        "credenziali sbagliate",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }

    }


    /** Al click del pulsante accedi vengono controllate le credenziali
    e se corrette viene effettuato l'accesso*/

    private fun onclickaccedi() {
        val email = binding.editTextEmailLogin.text.toString()
        val password = binding.editTextPasswordLogin.text.toString()

       if (inputCorretto.isValidEmail(email)) {
            if (inputCorretto.isValidPassword(password)) {
                viewModel.accedi(email, password)

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






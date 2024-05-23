package com.example.unilife.View.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.unilife.Utils.InputCorretto
import com.example.unilife.ViewModel.RegistrazioneViewModel
import com.example.unilife.databinding.ActivityRegistrazioneBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class RegistrazioneActivity : AppCompatActivity() {

    companion object {

        private const val USERNAME_ERROR = "Inserisci l'username. Non immettere degli spazi."
        private const val EMAIL_ERROR = "Inserisci un'e-mail valida"
        private const val PASSWORD_ERROR = "Inserisci una password di almeno 6 caratteri"
    }

    private lateinit var binding: ActivityRegistrazioneBinding
    private val viewModel: RegistrazioneViewModel by viewModels()
    private val inputCorretto = InputCorretto()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrazioneBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.registrazioneButton.setOnClickListener {registraClick()}
        binding.loginText.setOnClickListener {loginClick()}




        }


    /** metodo per creare un account*/
    private fun registraClick() {

        val username = binding.editTextUsername.text.toString().trim()
        val email = binding.editTextRegistrazioneEmail.text.toString().trim()
        val password = binding.editTextRegistrazionePassword.text.toString().trim()


        when {
            !inputCorretto.isValidUsername(username) -> binding.editTextUsername.error =
                USERNAME_ERROR

            !inputCorretto.isValidEmail(email) -> binding.editTextRegistrazioneEmail.error =
                EMAIL_ERROR

            !inputCorretto.isValidPassword(password) -> binding.editTextRegistrazionePassword.error =
                PASSWORD_ERROR

            else -> {

                lifecycleScope.launch {
                    val unico = viewModel.verificaUnicitaCredenziali(email, username)
                    if (unico) {

                        viewModel.registraUtente(email, password, username)

                        //viewModel.fireStoreUtente(email,username,password)

                        startActivity(Intent(this@RegistrazioneActivity, AccessoActivity::class.java))
                        finish()

                    } else {
                        Snackbar.make(
                            binding.root,
                            "trova un nuovo username o una nuova email",
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        }
    }

    /** metodo per accedere direttamente all'interfaccia' di login*/

    private fun loginClick(){
        startActivity(Intent(this, AccessoActivity::class.java))
        finish()
    }


        }


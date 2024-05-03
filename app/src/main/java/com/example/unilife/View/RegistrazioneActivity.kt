package com.example.unilife.View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.unilife.R
import com.example.unilife.ViewModel.RegistrazioneViewModel
import com.google.firebase.auth.FirebaseAuth
import com.example.unilife.databinding.ActivityRegistrazioneBinding

class RegistrazioneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrazioneBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val viewModel: RegistrazioneViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrazioneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.registrazioneButton.setOnClickListener {registraClick()}
        binding.loginText.setOnClickListener {loginClick()}
        }


    /** metodo per creare un account*/
    private fun registraClick() {

        val username = binding.editTextUsername.text.toString()
        val email = binding.editTextRegistrazioneEmail.text.toString()
        val password = binding.editTextRegistrazionePassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()) {

            viewModel.registraUtente(email, password, username)

        }
    }
    /** metodo per accedere direttamente all'interfaccia' di login*/

    private fun loginClick(){
        startActivity(Intent(this, AccessoActivity::class.java))
        finish()
    }
        }


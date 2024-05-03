package com.example.unilife.View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unilife.R
import com.google.firebase.auth.FirebaseAuth
import com.example.unilife.databinding.ActivityRegistrazioneBinding

class RegistrazioneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrazioneBinding
    private lateinit var firebaseAuth: FirebaseAuth
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

        val email = binding.editTextRegistrazioneEmail.text.toString()
        val password = binding.editTextRegistrazionePassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this, AccessoActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show()
                    }

                }

        } else {
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
        }
    }
    /** metodo per accedere direttamente all'interfaccia' di login*/
    private fun loginClick(){
        startActivity(Intent(this, AccessoActivity::class.java))
        finish()
    }
        }


package com.example.unilife.View.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.unilife.R
import com.example.unilife.ViewModel.ModificaViewModel
import com.example.unilife.databinding.ActivityModificaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ModificaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityModificaBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val viewModel: ModificaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityModificaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.button.setOnClickListener {
            handleUpdate()
        }
    }

    private fun handleUpdate() {
        val newUsername = binding.changeUsername.text.toString()
        val newPassword = binding.changePassword.text.toString()

        if (newUsername.isNotEmpty() || newPassword.isNotEmpty()) {
            if (newUsername.isNotEmpty()) {
                viewModel.updateUsername(newUsername)
            }

            if (newPassword.isNotEmpty()) {
                viewModel.updatePassword(newPassword)
            }
        } else {
            Toast.makeText(this, "Please enter new username or password", Toast.LENGTH_SHORT).show()
        }
    }
}



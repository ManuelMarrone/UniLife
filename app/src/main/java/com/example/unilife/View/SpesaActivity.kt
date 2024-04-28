package com.example.unilife.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.unilife.R
import com.example.unilife.databinding.ActivitySpesaBinding

class SpesaActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySpesaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_spesa)
    }

}
package com.example.unilife.View.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.unilife.databinding.ActivityPdfViewerBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class PdfViewerActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPdfViewerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPdfViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root?.setOnApplyWindowInsetsListener { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fileName = intent.extras?.getString("nome_doc")
        val url = intent.extras?.getString("url")



        lifecycleScope.launch(Dispatchers.IO) {

            val inputStream = URL(url).openStream()
            withContext(Dispatchers.Main){
                binding.pdfView.fromStream(inputStream).onRender{
                    pages, pageWidth: Float, pageHeight: Float ->
                    if(pages >= 1){
                        binding.progressBar.visibility = View.GONE
                    }
                }.load()
            }
        }
    }
}
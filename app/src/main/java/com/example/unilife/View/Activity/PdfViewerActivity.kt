package com.example.unilife.View.Activity

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.unilife.databinding.ActivityPdfViewerBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

class PdfViewerActivity : AppCompatActivity(), DownloadProgressUpdater.DownloadProgessListener {

    private lateinit var binding : ActivityPdfViewerBinding
    private lateinit var downloadManager: DownloadManager
    private var downloadProgressUpdater : DownloadProgressUpdater? = null
    private lateinit var snackbar: Snackbar

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
        snackbar = Snackbar.make(binding.mainLayout, "", Snackbar.LENGTH_INDEFINITE)
        val fileName = intent.extras?.getString("nome_doc")
        val url = intent.extras?.getString("url")
        downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager


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
        binding.floatingActionButton.setOnClickListener {
            downloadPdf(url, fileName)
        }

    }


    private fun downloadPdf(downloadUrl: String?, fileName: String?){
        try {
            val downloadUri = Uri.parse(downloadUrl)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(fileName)
                .setMimeType("application/pdf")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    File.separator + fileName
                )
            val downloadId = downloadManager.enqueue(request)
            downloadProgressUpdater = DownloadProgressUpdater(downloadManager, downloadId, this)
            binding.progressBar.visibility = View.VISIBLE

            lifecycleScope.launch(Dispatchers.IO){
                downloadProgressUpdater?.run()
            }
            snackbar.show()
        }catch (e : Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun updateProgress(progress: Long) {
        lifecycleScope.launch(Dispatchers.Main){

            when(progress){

                STATUS_SUCCESS -> {
                    snackbar.setText("Downloading... ${progress.toInt()}%")
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@PdfViewerActivity, "Download Successfully!!", Toast.LENGTH_SHORT).show()
                    snackbar.dismiss()
                }
                STATUS_FAILED -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@PdfViewerActivity, "Download Failed!!", Toast.LENGTH_SHORT).show()
                    snackbar.dismiss()
                }
                else -> {
                    snackbar.setText("Downloading... ${progress.toInt()}%")
                }
            }

        }

        }
    }

package com.example.unilife.View.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.unilife.Model.Documento
import com.example.unilife.R
import com.example.unilife.View.Adapter.PdfFilesAdapter
import com.example.unilife.databinding.ActivityAllPdfBinding
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

class AllPdfActivity : AppCompatActivity(), PdfFilesAdapter.PdfClickListener {

    private lateinit var binding: ActivityAllPdfBinding
    private lateinit var storage : StorageReference
    private lateinit var adapter: PdfFilesAdapter
    private lateinit var firestore : CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAllPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = FirebaseFirestore.getInstance().collection("documenti")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        initRecyclerView()
        getAllDocument()

    }

    private fun initRecyclerView() {
        binding.pdfsRecyclerView.setHasFixedSize(true)
        binding.pdfsRecyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = PdfFilesAdapter(this)
        binding.pdfsRecyclerView.adapter = adapter

    }



    private fun getAllDocument() {

            firestore.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Toast.makeText(this@AllPdfActivity, error.message.toString(), Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val tempList = mutableListOf<Documento>()
                    for (document in snapshot.documents) {
                        val pdfFile = document.toObject(Documento::class.java)
                        if (pdfFile != null) {
                            tempList.add(pdfFile)
                        }
                    }
                    if (tempList.isEmpty()) {
                        Toast.makeText(this@AllPdfActivity, "No data found", Toast.LENGTH_SHORT).show()
                    } else {
                        adapter.submitList(tempList)
                    }
                } else {
                    Toast.makeText(this@AllPdfActivity, "No data found", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onPdfClicked(pdfFile: Documento) {
       val intent = Intent(this, PdfViewerActivity::class.java)
        intent.putExtra("fileName", pdfFile.nome_doc)
        intent.putExtra("url", pdfFile.url)
        startActivity(intent)
    }
}
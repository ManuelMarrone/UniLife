package com.example.unilife.View.Activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.unilife.Model.Documento
import com.example.unilife.R
import com.example.unilife.View.Adapter.PdfFilesAdapter
import com.example.unilife.ViewModel.ArchivioViewModel
import com.example.unilife.databinding.ActivityAllPdfBinding
import com.google.firebase.firestore.CollectionReference

class AllPdfActivity : AppCompatActivity(), PdfFilesAdapter.PdfClickListener {

    private lateinit var binding: ActivityAllPdfBinding
    private lateinit var adapter: PdfFilesAdapter
    private lateinit var firestore : CollectionReference
    private val viewModel: ArchivioViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAllPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)


                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                    insets
                }

                setupViewModel()

    }

    private fun setupViewModel() {
        viewModel.getIdGruppo()
        viewModel.idGruppoUtente.observe(this) { groupId ->

            if (!groupId.isNullOrEmpty()) {
                firestore = viewModel.getFirestoreCollection(groupId)
                initRecyclerView()
                getAllDocument()
             }
        }

    }
    private fun initRecyclerView() {
        binding.pdfsRecyclerView.setHasFixedSize(true)
        binding.pdfsRecyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = PdfFilesAdapter(this)
        binding.pdfsRecyclerView.adapter = adapter

    }

    private fun getAllDocument() {
        viewModel.getAllDocument(
            firestore,
            onSuccess = { documentList ->
                if (documentList.isEmpty()) {
                    Toast.makeText(this@AllPdfActivity, "No data found", Toast.LENGTH_SHORT).show()
                } else {
                    adapter.submitList(documentList)
                }
            },
            onFailure = { errorMessage ->
                Toast.makeText(this@AllPdfActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun onPdfClicked(pdfFile: Documento) {
       val intent = Intent(this, PdfViewerActivity::class.java)
        intent.putExtra("fileName", pdfFile.nome_doc)
        intent.putExtra("url", pdfFile.url)
        startActivity(intent)
    }
    override fun onPdfDelete(pdfFile: Documento) {
        (adapter as PdfFilesAdapter).submitList((adapter as PdfFilesAdapter).currentList.filter { it != pdfFile })
        Toast.makeText(this@AllPdfActivity, "Documento eliminato", Toast.LENGTH_SHORT).show()
        viewModel.getIdGruppo()
        viewModel.idGruppoUtente.observe(this) { groupId ->

            if (!groupId.isNullOrEmpty()) {
                firestore = viewModel.getFirestoreCollection(groupId)
        viewModel.eliminaDocumento(
            groupId,
            pdfFile.id_documento,
            onSuccess = {
                Log.d("eliminazione", "Documento eliminato con successo")
            },
            onFailure = { e ->
                Log.e("eliminazione", "Errore durante l'eliminazione del documento ${pdfFile.id_documento}: $e")
                Toast.makeText(this@AllPdfActivity, "Errore durante l'eliminazione del documento", Toast.LENGTH_SHORT).show()
            }
        )
    }}}
}

// eliminazione Storage e tasto indietro
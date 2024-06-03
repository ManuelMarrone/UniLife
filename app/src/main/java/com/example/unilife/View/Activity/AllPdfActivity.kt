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
import com.example.unilife.Repository.ArchivioRepo
import com.example.unilife.View.Adapter.PdfFilesAdapter
import com.example.unilife.ViewModel.ArchivioViewModel
import com.example.unilife.databinding.ActivityAllPdfBinding
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.StorageReference

class AllPdfActivity : AppCompatActivity(), PdfFilesAdapter.PdfClickListener {

    private lateinit var binding: ActivityAllPdfBinding
    private lateinit var storage : StorageReference
    private lateinit var adapter: PdfFilesAdapter
    private lateinit var firestore : CollectionReference
    private val viewModel: ArchivioViewModel by viewModels()
    private val archivioRepo = ArchivioRepo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAllPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Sostituisci con l'ID effettivo del gruppo desiderato


       // viewModel.getIdGruppo()
      //  viewModel.idGruppoUtente.observe(this) { groupId ->
         //   if (!groupId.isNullOrEmpty()) {
           //     firestore = viewModel.getFirestoreCollection(groupId)

                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                    insets
                }

                setupViewModel()

              //  getAllDocument()
           // } else {
            //    Toast.makeText(this, "ID del gruppo non disponibile", Toast.LENGTH_SHORT).show()
           // }
      //  }


        /** val utenteRepo = UtenteRepo()
        val utenteTask = utenteRepo.getUtente()

        utenteTask.addOnSuccessListener { documentSnapshot ->
        val groupId = documentSnapshot?.getString("id_gruppo")
        if (!groupId.isNullOrEmpty()) {
        firestore = viewModel.getFirestoreCollection(groupId)
        //viewModel.fetchDocumenti(groupId)

        //firestore = FirebaseFirestore.getInstance().collection("gruppi").document(groupId).collection("documenti")
        //firestore = archivioRepo.getFirestoreCollection(groupId)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
        }

        initRecyclerView()
        getAllDocument()
        } else {
        Toast.makeText(this, "ID del gruppo non disponibile", Toast.LENGTH_SHORT).show()
        }
        }.addOnFailureListener { e ->
        Toast.makeText(this, "Errore durante il recupero dell'utente: ${e.message}", Toast.LENGTH_SHORT).show()
        }*/
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


  /** private fun getAllDocument() {

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
    }*/

    override fun onPdfClicked(pdfFile: Documento) {
       val intent = Intent(this, PdfViewerActivity::class.java)
        intent.putExtra("fileName", pdfFile.nome_doc)
        intent.putExtra("url", pdfFile.url)
        startActivity(intent)
    }
    override fun onPdfDelete(pdfFile: Documento) {
        // Rimuovi l'elemento dalla RecyclerView
        (adapter as PdfFilesAdapter).submitList((adapter as PdfFilesAdapter).currentList.filter { it != pdfFile })
        Toast.makeText(this@AllPdfActivity, "Documento eliminato", Toast.LENGTH_SHORT).show()
        viewModel.getIdGruppo()
        viewModel.idGruppoUtente.observe(this) { groupId ->

            if (!groupId.isNullOrEmpty()) {
                firestore = viewModel.getFirestoreCollection(groupId)
        // Chiama direttamente il metodo del ViewModel per eliminare il file
        viewModel.eliminaDocumento(
            groupId,
            pdfFile.id_documento,
            onSuccess = {
                // Gestisci il successo dell'eliminazione
                Log.d("eliminazione", "Documento eliminato con successo")
            },
            onFailure = { e ->
                // Gestisci il fallimento dell'eliminazione
                Log.e("eliminazione", "Errore durante l'eliminazione del documento ${pdfFile.id_documento}: $e")
                Toast.makeText(this@AllPdfActivity, "Errore durante l'eliminazione del documento", Toast.LENGTH_SHORT).show()
            }
        )
    }}}
}
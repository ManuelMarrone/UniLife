package com.example.unilife.View

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.unilife.R
import com.example.unilife.Utils.InputCorretto
import com.example.unilife.Utils.showSnackbar
import com.example.unilife.databinding.FragmentArchivioBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.util.Date

const val PERMISSION_REQUEST_STORAGE = 0

class ArchivioFragment : Fragment(), ActivityCompat.OnRequestPermissionsResultCallback {


    private lateinit var binding: FragmentArchivioBinding
    private val storage = FirebaseStorage.getInstance()

    private val inputCorretto = InputCorretto()

    private val appCompatActivity = AppCompatActivity()

    private lateinit var recyclerView: RecyclerView
    private lateinit var layout: View



    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        it?.let{
            if(it){
                this.makeToast("permission garanted")
            }
        }
    }

    private val fileAccess = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let { uri ->
                val inputStream = requireContext().contentResolver.openInputStream(uri)
                inputStream?.readBytes()?.let { byteArray ->
                    uploadFile(byteArray)
                }
            }
        }
    }







    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArchivioBinding.inflate(inflater, container, false)

        layout = binding.root


        binding.uploadButton.setOnClickListener {
            requestStoragePermission(requireContext()) {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "*/*"
                fileAccess.launch(intent)
            }
        }

        return layout

    }



    companion object {
        fun newInstance() = ArchivioFragment()
        private const val REQUEST_STORAGE_PERMISSION_CODE = 100
        private const val REQUEST_FILE_PICKER = 101
    }


    fun requestStoragePermission(context: Context, call: () -> Unit){

        if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            call.invoke()
        }else{
            requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    fun makeToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

    fun uploadFile(byteArray: ByteArray){
        val storageRef = storage.reference

        storageRef.child("images/${Date().time}").putBytes(byteArray)
            .addOnSuccessListener{
                this.makeToast("success")
            }
            .addOnFailureListener{
                this.makeToast("failure")
            }
            .addOnCompleteListener{
                this.makeToast("complete")
            }
    }

/**
    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openFilePicker()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_STORAGE_PERMISSION_CODE
            )
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*"
        startActivityForResult(intent, REQUEST_FILE_PICKER)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_FILE_PICKER && resultCode == RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                uploadFileToFirebase(uri)
            }
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openFilePicker()
            } else {
                Snackbar.make(
                    binding.root,
                    R.string.permission_denied_message,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun uploadFileToFirebase(fileUri: Uri) {
        val storageRef = Firebase.storage.reference
        val fileReference = storageRef.child("file/${fileUri.lastPathSegment}")
        val uploadTask = fileReference.putFile(fileUri)

        uploadTask.addOnSuccessListener {
            Snackbar.make(
                binding.root,
                R.string.upload_success_message,
                Snackbar.LENGTH_SHORT
            ).show()
        }.addOnFailureListener {
            Snackbar.make(
                binding.root,
                R.string.upload_failure_message,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }



    fun requestStoragePermission(context: Context, call:()->Unit) {
        if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            layout.showSnackbar(R.string.storage_permission_available, Snackbar.LENGTH_SHORT)
            call.invoke()

        }else{
            requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                //requestPermission()
        }
    }


    private fun StoragePermission() { // verifica se deve essere mostrato un messaggio di feedback all'utente con uno snackbar e succede solo se ho precedentemente un determinato permesso
        // Permission has not been granted and must be requested.
        if (shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

            layout.showSnackbar(R.string.storage_access_required,
                Snackbar.LENGTH_INDEFINITE, R.string.ok) {
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_STORAGE)
            }

        }

    }
*/

}
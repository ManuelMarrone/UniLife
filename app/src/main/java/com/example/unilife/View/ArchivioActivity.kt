package com.example.unilife.View

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.unilife.Model.Documento
import com.example.unilife.R
//import com.example.unilife.Repository.ArchivioRepo
import com.example.unilife.Utils.showSnackbar
import com.example.unilife.ViewModel.ArchivioViewModel
//import com.example.unilife.ViewModel.ArchivioViewModel
import com.example.unilife.databinding.ActivityAccessoBinding
import com.example.unilife.databinding.ActivityArchivioBinding
import com.example.unilife.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.androidgamesdk.gametextinput.Settings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.Date

const val _REQUEST_PERMISSION_STORAGE = 0
class ArchivioActivity : AppCompatActivity(),  ActivityCompat.OnRequestPermissionsResultCallback {
    private lateinit var binding: ActivityArchivioBinding
    private val storage = FirebaseStorage.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var layout: View
    private lateinit var viewModel: ArchivioViewModel

    //  private val archivioRepo = ArchivioRepo(this)
  //  private val archivioViewModel = ArchivioViewModel()





    private val selectFileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let { uri ->
                contentResolver.openInputStream(uri)?.use { inputStream ->
                    val bytes = inputStream.readBytes()
                    viewModel.uploadFile(bytes, uri)
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityArchivioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        layout = binding.root
        viewModel = ViewModelProvider(this).get(ArchivioViewModel::class.java)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        binding.uploadButton.setOnClickListener {
            // Handle button click
            showStoragePreview()

        }
    }

    private fun showStoragePreview() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                Log.d("Permission", "Permission granted")
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                layout.showSnackbar(R.string.storage_permission_available, Snackbar.LENGTH_SHORT)
                openFileExplorer()
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show()
                Log.d("Permission", "Permission not granted")
                requestStoragePermission()
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("Permission", "Permission granted")
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                layout.showSnackbar(R.string.storage_permission_available, Snackbar.LENGTH_SHORT)
                openFileExplorer()
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show()
                Log.d("Permission", "Permission not granted")
                requestStoragePermission()
            }
        }
    }

    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent =
                    Intent(android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String.format("package:%s", applicationContext.packageName))
                startActivityForResult(intent, _REQUEST_PERMISSION_STORAGE)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(intent, _REQUEST_PERMISSION_STORAGE)
            }
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                layout.showSnackbar(
                    R.string.storage_access_required,
                    Snackbar.LENGTH_INDEFINITE,
                    R.string.ok
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        _REQUEST_PERMISSION_STORAGE
                    )
                }
            } else {
                layout.showSnackbar(
                    R.string.storage_permission_not_available,
                    Snackbar.LENGTH_SHORT
                )
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    _REQUEST_PERMISSION_STORAGE
                )
            }
        }
    }


   private fun openFileExplorer() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        selectFileLauncher.launch(intent)

    }





    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == _REQUEST_PERMISSION_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Permission", "Permission granted")
                Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show()
                layout.showSnackbar(R.string.storage_permission_granted, Snackbar.LENGTH_SHORT)
                openFileExplorer()
            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
                Log.d("Permission", "Permission denied")
                layout.showSnackbar(R.string.storage_permission_denied, Snackbar.LENGTH_SHORT)
            }
        }
    }


    fun Context.makeToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }




}
package com.umb.gestiondapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.form.*
import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * Created By Juan Felipe Arango on 2/11/20
 * Copyright ©2020 Merqueo. All rights reserved
 */
class UploadProduct : AppCompatActivity() {

    val database = Firebase.database
    val dbRef = database.getReference("/")
    val storage = Firebase.storage
    val storageRef = storage.reference
    var photoUrl: Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form)

        button.setOnClickListener {
            validatePermission()
        }

        val product = Product(
            "Silla test",
            5000,
            "Phillips",
            "EX420",
            "Bogotá",
            "Nuevo",
            "www.phto.com"
        )
        dbRef.child("Activo")
            .child("random")
            .child(UUID.randomUUID().toString())
            .setValue(product)


    }

    private fun uploadImage(pathFile: String) {
        val stream = FileInputStream(File(pathFile))

        val uploadTask = storageRef.child(UUID.randomUUID().toString()).putStream(stream)
        uploadTask.addOnFailureListener {
            print(it)
        }.addOnSuccessListener { taskSnapshot ->
            print(taskSnapshot)
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, UPLOAD_IMAGE)
    }

    private fun validatePermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION
            )
        } else {
            pickImageFromGallery()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSION -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    pickImageFromGallery()
                    return
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                UPLOAD_IMAGE -> getImageGalley(data)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getImageGalley(data: Intent?) {
        data?.data?.let { uri ->
            val filePath = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor? = contentResolver.query(
                uri,
                filePath,
                null,
                null,
                null
            )
            cursor?.moveToFirst()
            val columnIndex: Int = cursor?.getColumnIndex(filePath[0]) ?: 0
            val picturePath: String? = cursor?.getString(columnIndex)
            cursor?.close()
            photoUrl = Uri.parse(picturePath)

            uploadImage(photoUrl!!.path!!)
        }
    }

    companion object {
        const val UPLOAD_IMAGE = 27
        const val STORAGE_PERMISSION = 420
    }
}

data class Product(
    val nombre: String,
    val precio: Int,
    val marca: String,
    val serie: String,
    val ubicacion: String,
    val estado: String,
    val fotoUrl: String
)
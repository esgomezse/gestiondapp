package com.umb.gestiondapp

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.umb.gestiondapp.LocationsActivity.Companion.LOCATION
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
    var dbRef = database.getReference("/")
    val storage = Firebase.storage
    val storageRef = storage.reference
    var photoUrl: Uri? = null
    private var product = Product()
    private val micros = ArrayList<String>()
    private val location = intent.getStringExtra(LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form)
        initListeners()
        initLocationAdapter()
        getMicros()
    }

    private fun initLocationAdapter() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf("Activo", "Pasivo", "users")
        )
        auTxvSelectLocation.setAdapter(adapter)
    }

    private fun initListeners() {
        button.setOnClickListener {
            validatePermission()
        }

        edtEstado.addTextChangedListener {
            product.estado = it?.toString() ?: ""
            button2.isEnabled = product.enableButton()
        }

        edtMarca.addTextChangedListener {
            product.marca = it?.toString() ?: ""
            button2.isEnabled = product.enableButton()
        }

        edtModelo.addTextChangedListener {
            product.modelo = it?.toString() ?: ""
            button2.isEnabled = product.enableButton()
        }

        edtNombre.addTextChangedListener {
            product.nombre = it?.toString() ?: ""
            button2.isEnabled = product.enableButton()
        }

        edtPrice.addTextChangedListener {
            product.precio = it?.toString()?.toIntNotEmpty() ?: 0
            button2.isEnabled = product.enableButton()
        }

        edtSerie.addTextChangedListener{
            product.serie = it?.toString()?: ""
            button2.isEnabled = product.enableButton()
        }

        button2.setOnClickListener {
            saveProduct()
        }

    }

    fun getMicros(){
        dbRef = database.getReference(location)
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                micros.clear()
                dataSnapshot.children.forEach {
                    micros.add(it.key.toString())
                }
                setMicros()
            }

            override fun onCancelled(error: DatabaseError) {

                print(error.toException())
            }
        })
    }

    private fun setMicros() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            micros
        )
        auTxvMicro.setAdapter(adapter)
    }

    private fun saveProduct() {
        dbRef = database.getReference("/")
        dbRef.child(auTxvSelectLocation.text.toString())
            .child(auTxvMicro.text.toString())
            .child(Date().toString())
            .setValue(product).addOnSuccessListener {
                Toast.makeText(this, "Producto subido con éxito", Toast.LENGTH_SHORT).show()
                clearFields()
            }
    }

    private fun clearFields() {
        product = Product()
        edtEstado.setText("")
        edtSerie.setText("")
        edtPrice.setText("")
        edtNombre.setText("")
        edtMarca.setText("")
        edtModelo.setText("")
        auTxvMicro.setText("")
        auTxvSelectLocation.setText("")
        button2.isEnabled = product.enableButton()

    }

    private fun uploadImage(pathFile: String) {
        val stream = FileInputStream(File(pathFile))

        val uploadTask = storageRef.child(UUID.randomUUID().toString()).putStream(stream)
        uploadTask.addOnFailureListener {
            Toast.makeText(this, "Algo salio mal, intentalo nuevamente", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener { taskSnapshot ->
            taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                Toast.makeText(this, "Foto subida exitosamente", Toast.LENGTH_SHORT).show()
                product.fotoUrl = it.toString()
                button2.isEnabled = product.enableButton()
            }
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

private fun String?.toIntNotEmpty(): Int {
    return if(this.isNullOrEmpty()) 0
    else this.toInt()
}

data class Product(
    var nombre: String = "",
    var precio: Int = 0,
    var marca: String = "",
    var serie: String = "",
    var estado: String = "",
    var modelo: String = "",
    var fotoUrl: String = ""
) {
    fun enableButton() =
        nombre.isNotEmpty() && precio != 0 && marca.isNotEmpty() && serie.isNotEmpty() &&
                estado.isNotEmpty() && fotoUrl.isNotEmpty() && modelo.isNotEmpty()

}
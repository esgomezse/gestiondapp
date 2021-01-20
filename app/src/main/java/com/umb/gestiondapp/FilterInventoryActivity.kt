package com.umb.gestiondapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.umb.gestiondapp.LocationsActivity.Companion.LOCATION
import com.umb.gestiondapp.LocationsActivity.Companion.SUCCESS
import com.umb.gestiondapp.adapters.InventoryAdapter
import com.umb.gestiondapp.models.InventoryModel
import com.umb.gestiondapp.models.LoanModel
import kotlinx.android.synthetic.main.activity_inventory_filter.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created By Juan Felipe Arango on 10/01/21
 * Copyright ©2020 Merqueo. All rights reserved
 */
class FilterInventoryActivity : AppCompatActivity() {

    private val inventoryAdapter by lazy { InventoryAdapter() }
    private val database = Firebase.database
    private lateinit var myRef: DatabaseReference
    private val inventory = ArrayList<InventoryModel>()
    private var location = ""
    private var typeFilter = 0
    private var loan: LoanModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_filter)
        location = intent.getStringExtra(LOCATION) ?: ""
        loan = intent.getParcelableExtra(LoanActivity.ITEM_LOAN)
        myRef = database.getReference("/$location")
        rcvInventory.adapter = inventoryAdapter
        rcvInventory.layoutManager = LinearLayoutManager(this)
        initFirebaseListener()
        initObservers()
    }

    private fun initObservers() {
        edtFilterWord.addTextChangedListener { str ->
            if (str.isNullOrEmpty()) {
                print("test")
                inventoryAdapter.setList(inventory, loan)
            } else {
                val upperStr = str.toString().toUpperCase(Locale.getDefault())
                val filteredList = when (typeFilter) {
                    NAME -> inventory.filter {
                        it.name.toUpperCase(Locale.getDefault()).contains(upperStr)
                    }
                    BRAND -> inventory.filter {
                        it.brand.toUpperCase(Locale.getDefault()).contains(upperStr)
                    }
                    MODEL -> inventory.filter {
                        it.model.toUpperCase(Locale.getDefault()).contains(upperStr)
                    }
                    SERIE -> inventory.filter {
                        it.serie.toUpperCase(Locale.getDefault()).contains(upperStr)
                    }
                    PRICE -> inventory.filter { it.price.toString() == str.toString() }
                    LOCATION_FIELD -> inventory.filter {
                        it.location.toUpperCase(Locale.getDefault()).contains(upperStr)
                    }
                    STATUS -> inventory.filter {
                        it.status.toUpperCase(Locale.getDefault()).contains(upperStr)
                    }
                    ISO -> inventory.filter {
                        it.iso.toUpperCase(Locale.getDefault()).contains(upperStr)
                    }
                    else -> emptyList()
                }
                inventoryAdapter.setList(filteredList, loan)
            }
        }

        spinner2.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                typeFilter = position
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        inventoryAdapter.events.observe(this, androidx.lifecycle.Observer {
            saveProduct(it)
        })
    }

    private fun saveProduct(inventoryModel: InventoryModel) {
        inventoryModel.usuarioPrestamo = loan?.id ?: ""

        val loanMap = loan?.toMap()?.toMutableMap() ?: mutableMapOf()

        loanMap["Producto"] = inventoryModel.toMap()
        loanMap["ProductoUbicacion"] = location
        loanMap["ProductoID"] = inventoryModel.id

        val childUpdates = hashMapOf<String, Any>(
            "/prestamos/${loan!!.id}" to loanMap,
            "/$location/${inventoryModel.id}" to inventoryModel.toMap()
        )
        myRef = database.getReference("/")
        myRef.updateChildren(childUpdates).addOnSuccessListener {
            setResult(SUCCESS)
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Algo ha salido mal, vuelve a intentarlo", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun initFirebaseListener() {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                inventory.clear()
                dataSnapshot.children.forEach {
                    val loanModel = it.getValue<InventoryModel>() ?: InventoryModel()
                    loanModel.id = it.key ?: ""
                    inventory.add(loanModel)
                }
                inventoryAdapter.setList(inventory, loan)
                pgbar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                print(error.toException())
            }
        })
    }

    companion object {
        const val NAME = 0
        const val BRAND = 1
        const val MODEL = 2
        const val SERIE = 3
        const val PRICE = 4
        const val LOCATION_FIELD = 5
        const val STATUS = 6
        const val ISO = 7
    }
}
package com.umb.gestiondapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.umb.gestiondapp.LocationsActivity.Companion.LOCATION
import com.umb.gestiondapp.adapters.InventoryAdapter
import com.umb.gestiondapp.models.InventoryModel
import kotlinx.android.synthetic.main.activity_inventory_filter.*


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_filter)
        location = intent.getStringExtra(LOCATION) ?: ""
        myRef = database.getReference("/$location")
        rcvInventory.adapter = inventoryAdapter
        rcvInventory.layoutManager = LinearLayoutManager(this)
        initFirebaseListener()
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
                inventoryAdapter.setList(inventory)
            }

            override fun onCancelled(error: DatabaseError) {
                print(error.toException())
            }
        })
    }
}
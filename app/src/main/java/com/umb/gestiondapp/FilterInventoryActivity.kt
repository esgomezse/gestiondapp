package com.umb.gestiondapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.umb.gestiondapp.adapters.InventoryAdapter
import com.umb.gestiondapp.models.InventoryModel
import kotlinx.android.synthetic.main.activity_inventory_filter.*


/**
 * Created By Juan Felipe Arango on 10/01/21
 * Copyright ©2020 Merqueo. All rights reserved
 */
class FilterInventoryActivity : AppCompatActivity() {

    private val inventoryAdapter by lazy { InventoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_filter)
        rcvInventory.adapter = inventoryAdapter
        rcvInventory.layoutManager = LinearLayoutManager(this)
        val list = listOf(
            InventoryModel(
                "Silla",
                "Volkswagen",
                "DRe",
                "RF",
                "10000",
                "Almacenamiento",
                "Nueva",
                ""
            ),
            InventoryModel(
                "Mesa",
                "Mazda",
                "DRe",
                "RF",
                "10000",
                "Almacenamiento",
                "Nueva",
                ""
            )
        )
        inventoryAdapter.setList(list)
    }
}
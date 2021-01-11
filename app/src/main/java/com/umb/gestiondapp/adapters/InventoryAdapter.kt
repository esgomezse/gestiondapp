package com.umb.gestiondapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umb.gestiondapp.R
import com.umb.gestiondapp.models.InventoryModel
import kotlinx.android.synthetic.main.item_inventory.view.*

/**
 * Created By Juan Felipe Arango on 10/01/21
 * Copyright ©2020 Merqueo. All rights reserved
 */
class InventoryAdapter: RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {

    private var listInventory = listOf<InventoryModel>()

    inner class InventoryViewHolder(v : View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_inventory, parent, false)
        return InventoryViewHolder(view)
    }

    override fun getItemCount() = listInventory.count()

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val itemInventory = listInventory[position]
        //holder.itemView.imageInventario
        with(holder.itemView){
            txvNombreInv.text = itemInventory.name
            txvMarcaInv.text = itemInventory.brand
            txvModeloInv.text = itemInventory.model
            txvSerieInv.text = itemInventory.serie
            txvCostoInv.text = itemInventory.price
            txvUbicacionInv.text = itemInventory.location
            txvEstadoInv.text = itemInventory.status
        }
    }

    fun setList(list: List<InventoryModel>){
        listInventory = list
    }

}
package com.umb.gestiondapp.adapters

import android.graphics.Typeface
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umb.gestiondapp.R
import com.umb.gestiondapp.boldTitle
import com.umb.gestiondapp.models.InventoryModel
import com.umb.gestiondapp.models.LoanModel
import com.umb.gestiondapp.setImageUrl
import kotlinx.android.synthetic.main.item_inventory.view.*

/**
 * Created By Juan Felipe Arango on 10/01/21
 * Copyright ©2020 Merqueo. All rights reserved
 */
class InventoryAdapter : RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {

    private var listInventory = mutableListOf<InventoryModel>()
    private var loanAdapter: LoanModel?=null
    var events = MutableLiveData<InventoryModel>()
    inner class InventoryViewHolder(v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_inventory, parent, false)
        return InventoryViewHolder(view)
    }

    override fun getItemCount() = listInventory.count()

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val itemInventory = listInventory[position]
        //holder.itemView.imageInventario
        with(holder.itemView) {
            txvNombreInv.boldTitle("Nombre: ", itemInventory.name)
            txvMarcaInv.boldTitle("Marca: ", itemInventory.brand)
            txvModeloInv.boldTitle("Modelo: ", itemInventory.model)
            txvSerieInv.boldTitle("Serie: ", itemInventory.serie)
            txvCostoInv.boldTitle("Costo: ", itemInventory.price.toString())
            txvUbicacionInv.boldTitle("Ubicación: ", itemInventory.location)
            txvEstadoInv.boldTitle("Estado: ", itemInventory.status)
            txvISO.boldTitle("ISO: ", itemInventory.iso)
            imageInventario.setImageUrl(itemInventory.image)
            btnConfirm.isVisible = loanAdapter!=null
            if(itemInventory.usuarioPrestamo.isNotEmpty()) {
                txvLoaned.isVisible = true
                btnConfirm.isEnabled = false
            }
            btnConfirm.setOnClickListener {
                events.value = itemInventory
            }
        }
    }

    fun setList(list: List<InventoryModel>, loan: LoanModel?) {
        listInventory.clear()
        listInventory.addAll(list)
        loanAdapter = loan
        notifyDataSetChanged()
    }

}
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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umb.gestiondapp.R
import com.umb.gestiondapp.models.InventoryModel
import kotlinx.android.synthetic.main.item_inventory.view.*

/**
 * Created By Juan Felipe Arango on 10/01/21
 * Copyright ©2020 Merqueo. All rights reserved
 */
class InventoryAdapter: RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {

    private var listInventory = mutableListOf<InventoryModel>()

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
            txvNombreInv.boldTitle("Nombre: " , itemInventory.name)
            txvMarcaInv.boldTitle("Marca: " , itemInventory.brand)
            txvModeloInv.boldTitle("Modelo: " , itemInventory.model)
            txvSerieInv.boldTitle("Serie: " , itemInventory.serie)
            txvCostoInv.boldTitle("Costo: " , itemInventory.price.toString())
            txvUbicacionInv.boldTitle("Ubicación: " , itemInventory.location)
            txvEstadoInv.boldTitle("Estado: " , itemInventory.status)
            txvISO.boldTitle("ISO: ", itemInventory.iso)
            imageInventario.setImageUrl(itemInventory.image)
        }
    }

    fun setList(list: List<InventoryModel>){
        listInventory.clear()
        listInventory.addAll(list)
        notifyDataSetChanged()
    }

}

fun TextView.boldTitle(title: String?, text: String?) {
    if (text==null || title==null) return
    val builder= SpannableStringBuilder(title)
    builder.setSpan(StyleSpan(Typeface.BOLD), 0, title.length , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    builder.append(" $text")
    this.text = builder
}

fun ImageView.setImageUrl(url: String){
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_image_not_supported)
        .into(this)
}
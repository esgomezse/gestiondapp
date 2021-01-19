package com.umb.gestiondapp.models

import com.google.firebase.database.PropertyName


/**
 * Created By Juan Felipe Arango on 11/01/21
 * Copyright ©2020 Merqueo. All rights reserved
 */
data class InventoryModel (
    var id: String ="",
    @get:PropertyName("nombre")
    @set:PropertyName("nombre")
    var name: String = "",
    @get:PropertyName("marca")
    @set:PropertyName("marca")
    var brand: String = "",
    @get:PropertyName("modelo")
    @set:PropertyName("modelo")
    var model: String = "",
    @get:PropertyName("serie")
    @set:PropertyName("serie")
    var serie: String = "",
    @get:PropertyName("precio")
    @set:PropertyName("precio")
    var price: Long = 0,
    @get:PropertyName("ubicacion")
    @set:PropertyName("ubicacion")
    var location: String = "",
    @get:PropertyName("estado")
    @set:PropertyName("estado")
    var status: String = "",
    @get:PropertyName("fotoUrl")
    @set:PropertyName("fotoUrl")
    var image: String = "",
    @get:PropertyName("iso")
    @set:PropertyName("iso")
    var iso: String = ""
)
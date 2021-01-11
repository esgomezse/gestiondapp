package com.umb.gestiondapp.models


/**
 * Created By Juan Felipe Arango on 11/01/21
 * Copyright ©2020 Merqueo. All rights reserved
 */
data class InventoryModel (
    val name: String,
    val brand: String,
    val model: String,
    val serie: String,
    val price: String,
    val location: String,
    val status: String,
    val image: String
)
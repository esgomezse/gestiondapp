package com.umb.gestiondapp.models


/**
 * Created By Juan Felipe Arango on 18/01/21
 * Copyright ©2020 Merqueo. All rights reserved
 */
data class Product(
    var nombre: String = "",
    var precio: Int = 0,
    var marca: String = "",
    var serie: String = "",
    var estado: String = "",
    var modelo: String = "",
    var ubicacion: String = "",
    var iso: String = "",
    var fotoUrl: String = ""
) {
    fun enableButton() =
        nombre.isNotEmpty() && precio != 0 && estado.isNotEmpty() && ubicacion.isNotEmpty()

}
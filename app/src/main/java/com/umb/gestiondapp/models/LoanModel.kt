package com.umb.gestiondapp.models

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoanModel(
    var id: String ="",
    @get:PropertyName("nombre")
    @set:PropertyName("nombre")
    var studname: String = "",
    @get:PropertyName("codigo")
    @set:PropertyName("codigo")
    var code: String = "",
    @get:PropertyName("ayuda")
    @set:PropertyName("ayuda")
    var loan: String = "",
    @get:PropertyName("asignatura")
    @set:PropertyName("asignatura")
    var subject: String = "",
    @get:PropertyName("curso")
    @set:PropertyName("curso")
    var group: String = "",
    @get:PropertyName("estado")
    @set:PropertyName("estado")
    var status: String = "",
    @get:PropertyName("fechaPrestamo")
    @set:PropertyName("fechaPrestamo")
    var date: String = "",
    @get:PropertyName("imagen")
    @set:PropertyName("imagen")
    var image: Image = Image(""),
    @get:PropertyName("ProductoID")
    @set:PropertyName("ProductoID")
    var productId: String = "",
    @get:PropertyName("ProductoUbicacion")
    @set:PropertyName("ProductoUbicacion")
    var productLocation: String = "",
    @get:PropertyName("Producto")
    @set:PropertyName("Producto")
    var product: InventoryModel = InventoryModel()
//
//    @get:PropertyName("ProductoISO")
//    @set:PropertyName("ProductoISO")
//    var productISO: String = "",
//    @get:PropertyName("ProductoImagen")
//    @set:PropertyName("ProductoImagen")
//    var productImagen: String = "",
//    @get:PropertyName("ProductoId")
//    @set:PropertyName("ProductoId")
//    var productId: String = ""

) : Parcelable {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nombre" to studname,
            "codigo" to code,
            "ayuda" to loan,
            "asignatura" to subject,
            "curso" to group,
            "fechaPrestamo" to date,
            "estado" to status,
            "imagen" to image
        )
    }
}
@Parcelize
data class Image(
    @get:PropertyName("encoded")
    @set:PropertyName("encoded")
    var encoded: String = ""
) : Parcelable


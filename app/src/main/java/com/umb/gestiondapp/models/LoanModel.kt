package com.umb.gestiondapp.models

import com.google.firebase.database.PropertyName

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
    var image: Image = Image("")
){
    data class Image(
        @get:PropertyName("encoded")
        @set:PropertyName("encoded")
        var encoded: String = ""
    )

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


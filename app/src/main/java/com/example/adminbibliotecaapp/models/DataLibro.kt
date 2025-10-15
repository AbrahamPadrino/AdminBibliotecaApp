package com.example.adminbibliotecaapp.models

import com.google.gson.annotations.SerializedName

data class DataLibro(
    @SerializedName("isbn")
    var isbn: String = "",
    @SerializedName("portada")
    var portada: String = "",
    @SerializedName("nom_libro")
    var nomLibro: String = "",
    @SerializedName("autor")
    var autor: String = "",
    @SerializedName("descripcion")
    var descripcion: String = "",
    @SerializedName("editorial")
    var editorial: String = "",
    @SerializedName("anio_publicacion")
    var anioPublicacion: String = "",
    @SerializedName("edicion")
    var edicion: String = "",
    @SerializedName("existencias")
    var existencias: Int = 0,
    @SerializedName("categoria")
    var categoria: String = ""
)
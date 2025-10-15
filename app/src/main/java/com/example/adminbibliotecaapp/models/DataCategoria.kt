package com.example.adminbibliotecaapp.models

import com.google.gson.annotations.SerializedName

data class DataCategoria(
    @SerializedName("id_categoria")
    var idCategoria: String = "",
    @SerializedName("nom_categoria")
    var nomCategoria: String = ""
) {
    override fun toString(): String {
        return "${idCategoria},${nomCategoria}"
    }
}
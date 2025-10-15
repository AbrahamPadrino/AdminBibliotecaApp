package com.example.adminbibliotecaapp.models

import com.google.gson.annotations.SerializedName

data class DataEditorial(
    @SerializedName("id_editorial")
    var idEditorial: String = "",
    @SerializedName("nom_editorial")
    var nomEditorial: String = ""
) {
    override fun toString(): String {
        return "${idEditorial},${nomEditorial}"
    }
}
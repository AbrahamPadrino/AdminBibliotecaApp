package com.example.adminbibliotecaapp.models

import com.google.gson.annotations.SerializedName

data class DataAutor(
    @SerializedName("id_autor")
    var idAutor: String = "",
    @SerializedName("nom_autor")
    var nomAutor: String = ""
) {
    override fun toString(): String {
        return "${idAutor}, ${nomAutor}"
    }
}
package com.example.adminbibliotecaapp.models

import com.google.gson.annotations.SerializedName

data class DataUsuario(
    @SerializedName("id_usuario")
    var idUsuario: String = "",
    @SerializedName("nom_usuario")
    var nomUsuario: String = "",
    @SerializedName("estado_usuario")
    var estadoUsuario: String = "",
    @SerializedName("contrasena")
    var contrasena: String = ""
) {
    override fun toString(): String {
        return "${idUsuario}, ${nomUsuario}"
    }
}
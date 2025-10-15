package com.example.adminbibliotecaapp.models

import com.google.gson.annotations.SerializedName

data class DataAdminUsuario(
    @SerializedName("id_usuario")
    val idUsuario: String,
    @SerializedName("nom_usuario")
    val nomUsuario: String,
    @SerializedName("contrasena")
    val contrasena: String,
)
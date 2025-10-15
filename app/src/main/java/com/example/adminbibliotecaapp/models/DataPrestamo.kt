package com.example.adminbibliotecaapp.models

import com.google.gson.annotations.SerializedName

data class DataPrestamo(
    @SerializedName("id_prestamo")
    val idPresta: String,
    @SerializedName("isbn")
    val isbn: String,
    @SerializedName("id_usuario")
    val idUsuario: String,
    @SerializedName("fecha_prestamo")
    val fechaPrestamo: String
)
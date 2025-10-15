package com.example.adminbibliotecaapp.response

import com.example.adminbibliotecaapp.models.DataAdminUsuario
import com.google.gson.annotations.SerializedName

data class AdminUsuarioResponse(
    @SerializedName("code")
    var code: String = "",
    @SerializedName("mensaje")
    var mensaje: String = "",
    @SerializedName("data")
    var data: List<DataAdminUsuario> = emptyList()
)
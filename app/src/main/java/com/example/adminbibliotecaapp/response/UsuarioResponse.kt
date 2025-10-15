package com.example.adminbibliotecaapp.response

import com.example.adminbibliotecaapp.models.DataUsuario
import com.google.gson.annotations.SerializedName

data class UsuarioResponse(
    @SerializedName("code")
    var code: String = "",
    @SerializedName("mensaje")
    var mensaje: String = "",
    @SerializedName("data")
    var data: List<DataUsuario> = emptyList()
)

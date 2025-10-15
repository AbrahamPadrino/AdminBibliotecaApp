package com.example.adminbibliotecaapp.response

import com.example.adminbibliotecaapp.models.DataCategoria
import com.google.gson.annotations.SerializedName

data class CategoriaResponse(
    @SerializedName("code")
    var code: String = "",
    @SerializedName("mensaje")
    var mensaje: String = "",
    @SerializedName("data")
    var data: List<DataCategoria> = emptyList()
)
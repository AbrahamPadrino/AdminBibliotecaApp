package com.example.adminbibliotecaapp.response

import com.example.adminbibliotecaapp.models.DataAutor
import com.google.gson.annotations.SerializedName

data class AutorResponse(
    @SerializedName("code")
    var code: String = "",
    @SerializedName("mensaje")
    var mensaje: String = "",
    @SerializedName("data")
    var data: List<DataAutor> = emptyList()
)
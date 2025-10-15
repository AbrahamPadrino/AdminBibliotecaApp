package com.example.adminbibliotecaapp.response

import com.example.adminbibliotecaapp.models.DataLibro
import com.google.gson.annotations.SerializedName

data class LibroResponse(
    @SerializedName("code")
    var code: String = "",
    @SerializedName("mensaje")
    var mensaje: String = "",
    @SerializedName("data")
    var data: List<DataLibro> = emptyList()
)
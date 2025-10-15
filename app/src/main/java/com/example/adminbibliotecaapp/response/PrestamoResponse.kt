package com.example.adminbibliotecaapp.response

import com.example.adminbibliotecaapp.models.DataPrestamo
import com.google.gson.annotations.SerializedName

data class PrestamoResponse(
    @SerializedName("code")
    var code: String = "",
    @SerializedName("mensaje")
    var mensaje: String = "",
    @SerializedName("data")
    var data: List<DataPrestamo> = emptyList()
)
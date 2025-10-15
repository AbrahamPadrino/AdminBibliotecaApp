package com.example.adminbibliotecaapp.response

import com.example.adminbibliotecaapp.models.DataEditorial
import com.google.gson.annotations.SerializedName

data class EditorialResponse(
    @SerializedName("code")
    var code: String = "",
    @SerializedName("mensaje")
    var mensaje: String = "",
    @SerializedName("data")
    var data: List<DataEditorial> = emptyList()
)
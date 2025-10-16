package com.example.adminbibliotecaapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminbibliotecaapp.models.DataPrestamo
import com.example.adminbibliotecaapp.network.RetrofitClient
import com.example.adminbibliotecaapp.response.PrestamoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PrestamoViewModel: ViewModel() {
    private var _listaPrestamos = MutableStateFlow<List<DataPrestamo>>(emptyList())
    val listaPrestamos = _listaPrestamos.asStateFlow()

    private lateinit var response: Response<PrestamoResponse>

    init {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.obtenerPrestamos()
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaPrestamos.value = response.body()!!.data
                }
            }
        }
    }

    fun obtenerPrestamos() {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.obtenerPrestamos()
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaPrestamos.value = response.body()!!.data
                }
            }
        }
    }

    fun devolverPrestamo(prestamo: DataPrestamo) {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.borrarPrestamo(prestamo)
            obtenerPrestamos()
        }
    }
}
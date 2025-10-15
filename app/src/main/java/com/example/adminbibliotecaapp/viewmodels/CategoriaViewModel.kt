package com.example.adminbibliotecaapp.viewmodels

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminbibliotecaapp.models.DataCategoria
import com.example.adminbibliotecaapp.network.RetrofitClient
import com.example.adminbibliotecaapp.response.CategoriaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class CategoriaViewModel: ViewModel() {
    //Variable para almacenar la respuesta de la consulta a la API.
    private lateinit var response: Response<CategoriaResponse>

    //Variable para el valor de la lista obtenida de la API
    private val _listaCategorias = MutableStateFlow<List<DataCategoria>>(emptyList())
    val listaCategorias = _listaCategorias.asStateFlow()

    // Para ejecutar al inicio de la instancia
    init {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.obtenerCategorias()
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaCategorias.value = response.body()!!.data
                }
            }
        }
    }
    // Funciones para agregar, editar y borrar
    fun agregarCategoria(categoria: DataCategoria) {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.agregarCategoria(categoria)
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaCategorias.value = RetrofitClient.webService.obtenerCategorias().body()!!.data
                }
            }
        }
    }

    fun editarCategoria(categoria: DataCategoria) {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.actualizarCategoria(categoria)
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaCategorias.value = RetrofitClient.webService.obtenerCategorias().body()!!.data
                }
            }
        }
    }

    fun borrarCategoria(categoria: DataCategoria) {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.borrarCategoria(categoria)
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaCategorias.value = RetrofitClient.webService.obtenerCategorias().body()!!.data
                }
            }
        }
    }
    // Función para validar los campos
    fun validarCampos(
        categoria: MutableState<DataCategoria>
    ): Boolean {
        return if(
            categoria.value.idCategoria.isEmpty() ||
            categoria.value.nomCategoria.isEmpty()
        ) {
            false
        } else {
            true
        }
    }

}
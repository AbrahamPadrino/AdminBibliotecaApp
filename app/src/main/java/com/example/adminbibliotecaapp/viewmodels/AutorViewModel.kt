package com.example.adminbibliotecaapp.viewmodels

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminbibliotecaapp.models.DataAutor
import com.example.adminbibliotecaapp.network.RetrofitClient
import com.example.adminbibliotecaapp.response.AutorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class AutorViewModel: ViewModel() {

    // Mantener la lista de autores obtenidos de la petición al backend.
    private val _listaAutores = MutableStateFlow<List<DataAutor>>(emptyList())
    val listaAutores = _listaAutores.asStateFlow()

    //Obtener y almacenar la respuesta del servidor.
    private lateinit var response: Response<AutorResponse>

    //Obtener la lista de Autores al instanciar el viewModel
    init {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.obtenerAutores()
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaAutores.value = response.body()!!.data
                }
            }
        }
    }

    fun agregarAutor(autor: DataAutor) {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.agregarAutor(autor)
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaAutores.value = response.body()!!.data
                }
            }
        }
    }

    fun editarAutor(autor: DataAutor) {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.actualizarAutor(autor)
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaAutores.value = response.body()!!.data
                }
            }
        }
    }

    fun borrarAutor(autor: DataAutor) {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.borrarAutor(autor)
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaAutores.value = response.body()!!.data
                }
            }
        }
    }

    fun validarCampos(
        autor: MutableState<DataAutor>
    ): Boolean {
        return if(
            autor.value.idAutor.isEmpty() ||
            autor.value.nomAutor.isEmpty()
        ) {
            false
        } else {
            true
        }
    }
}
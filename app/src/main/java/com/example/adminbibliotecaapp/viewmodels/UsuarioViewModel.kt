package com.example.adminbibliotecaapp.viewmodels

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminbibliotecaapp.models.DataUsuario
import com.example.adminbibliotecaapp.network.RetrofitClient
import com.example.adminbibliotecaapp.response.UsuarioResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class UsuarioViewModel: ViewModel() {
    private val _listaUsuarios = MutableStateFlow<List<DataUsuario>>(emptyList())
    val listaUsuarios = _listaUsuarios.asStateFlow()

    private lateinit var response: Response<UsuarioResponse>

    init {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.obtenerUsuarios()
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaUsuarios.value = response.body()!!.data
                }
            }
        }
    }

    fun agregarUsuario(usuario: DataUsuario) {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.agregarUsuario(usuario)
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaUsuarios.value = RetrofitClient.webService.obtenerUsuarios().body()!!.data
                }
            }
        }
    }

    fun editarUsuario(usuario: DataUsuario) {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.actualizarUsuario(usuario)
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaUsuarios.value = RetrofitClient.webService.obtenerUsuarios().body()!!.data
                }
            }
        }
    }

    fun borrarUsuario(usuario: DataUsuario) {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.borrarUsuario(usuario = usuario)
            //Log.d("API", response.body().toString())
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaUsuarios.value = RetrofitClient.webService.obtenerUsuarios().body()!!.data
                }
            }
        }
    }

    fun validarCampos(
        usuario: MutableState<DataUsuario>
    ): Boolean {
        return if (
            usuario.value.idUsuario.isEmpty() ||
            usuario.value.nomUsuario.isEmpty() ||
            usuario.value.contrasena.isEmpty()
        ) {
            false
        } else {
            true
        }
    }

}
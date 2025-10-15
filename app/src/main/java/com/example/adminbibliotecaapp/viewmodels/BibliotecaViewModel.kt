package com.example.adminbibliotecaapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminbibliotecaapp.models.DataAdminUsuario
import com.example.adminbibliotecaapp.network.RetrofitClient
import com.example.adminbibliotecaapp.response.AdminUsuarioResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class BibliotecaViewModel : ViewModel() {

    private var _admUsuarioResp = MutableLiveData<AdminUsuarioResponse>()
    val admUsuarioResp: LiveData<AdminUsuarioResponse> get() = _admUsuarioResp

    private lateinit var response: Response<AdminUsuarioResponse>   // Guarda la respuesta de la API

    fun login(usuario: DataAdminUsuario) {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.login(usuario)
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _admUsuarioResp.value = response.body()
                } else if(response.body()!!.code == "400") {
                    _admUsuarioResp.value = response.body()
                }
            }
        }
    }

    fun logout() {
        _admUsuarioResp.value?.code = ""
        _admUsuarioResp.value?.mensaje = ""
        _admUsuarioResp.value?.data = emptyList()
    }

    fun validarCampos(
        usuario: String,
        contrasena: String
    ): Boolean {
        return if(usuario.isEmpty() || contrasena.isEmpty()) {
            false
        } else {
            true
        }
    }

}
package com.example.adminbibliotecaapp.viewmodels

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminbibliotecaapp.models.DataEditorial
import com.example.adminbibliotecaapp.network.RetrofitClient
import com.example.adminbibliotecaapp.response.EditorialResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class EditorialViewModel: ViewModel() {
    private val _listaEditoriales = MutableStateFlow<List<DataEditorial>>(emptyList())
    val listaEditoriales = _listaEditoriales.asStateFlow()

    private lateinit var response: Response<EditorialResponse>

    init {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.obtenerEditoriales()
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaEditoriales.value = response.body()!!.data
                }
            }
        }
    }

    fun agregarEditorial(editorial: DataEditorial) {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.agregarEditorial(editorial)
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaEditoriales.value = response.body()!!.data
                }
            }
        }
    }

    fun editarEditorial(editorial: DataEditorial) {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.actualizarEditorial(editorial)
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaEditoriales.value = response.body()!!.data
                }
            }
        }
    }

    fun borrarEditorial(editorial: DataEditorial) {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.borrarEditorial(editorial)
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaEditoriales.value = response.body()!!.data
                }
            }
        }
    }

    fun validarCampos(
        editorial: MutableState<DataEditorial>
    ): Boolean {
        return if (
            editorial.value.idEditorial.isEmpty() ||
            editorial.value.nomEditorial.isEmpty()
        ) {
            false
        } else {
            true
        }
    }
}
package com.example.adminbibliotecaapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminbibliotecaapp.models.DataAutor
import com.example.adminbibliotecaapp.models.DataCategoria
import com.example.adminbibliotecaapp.models.DataEditorial
import com.example.adminbibliotecaapp.models.DataLibro
import com.example.adminbibliotecaapp.models.DataPrestamo
import com.example.adminbibliotecaapp.network.RetrofitClient
import com.example.adminbibliotecaapp.response.AutorResponse
import com.example.adminbibliotecaapp.response.CategoriaResponse
import com.example.adminbibliotecaapp.response.EditorialResponse
import com.example.adminbibliotecaapp.response.LibroResponse
import com.example.adminbibliotecaapp.response.PrestamoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LibroViewModel: ViewModel() {
    private val _listaLibros = MutableStateFlow<List<DataLibro>>(emptyList())
    val listaLibros = _listaLibros.asStateFlow()

    private val _listaAutores = MutableStateFlow<List<DataAutor>>(emptyList())
    val listaAutores = _listaAutores.asStateFlow()

    private val _listaEditoriales = MutableStateFlow<List<DataEditorial>>(emptyList())
    val listaEditoriales = _listaEditoriales.asStateFlow()

    private val _listaCategorias = MutableStateFlow<List<DataCategoria>>(emptyList())
    val listaCategorias = _listaCategorias.asStateFlow()

    private lateinit var response: Response<LibroResponse>
    private lateinit var respAutor: Response<AutorResponse>
    private lateinit var respEditorial: Response<EditorialResponse>
    private lateinit var respCategoria: Response<CategoriaResponse>
    private lateinit var respPrestamo: Response<PrestamoResponse>

    init {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.obtenerLibros()
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaLibros.value = response.body()!!.data
                }
            }
        }
        // Llamada a las funciones para mostrar los spinners.
        spinnerAutor()
        spinnerEditorial()
        spinnerCategoria()
    }

    fun agregarLibro(libro: DataLibro) {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.agregarLibro(libro)
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaLibros.value = RetrofitClient.webService.obtenerLibros().body()!!.data
                }
            }
        }
    }

    fun editarLibro(libro: DataLibro) {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.actualizarLibro(libro)
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaLibros.value = RetrofitClient.webService.obtenerLibros().body()!!.data
                }
            }
        }
    }

    fun borrarLibro(libro: DataLibro) {
        viewModelScope.launch(Dispatchers.IO) {
            response = RetrofitClient.webService.borrarLibro(libro)
            withContext(Dispatchers.Main) {
                if (response.body()!!.code == "200") {
                    _listaLibros.value = RetrofitClient.webService.obtenerLibros().body()!!.data
                }
            }
        }
    }

    fun prestarLibro(prestamo: DataPrestamo) {
        viewModelScope.launch(Dispatchers.IO) {
            respPrestamo = RetrofitClient.webService.agregarPrestamo(prestamo)
        }
    }

    // Funciones para Spinners.
    fun spinnerAutor() {
        viewModelScope.launch(Dispatchers.IO) {
            respAutor = RetrofitClient.webService.obtenerAutores()
            withContext(Dispatchers.Main) {
                _listaAutores.value = respAutor.body()!!.data
            }
        }
    }

    fun spinnerEditorial() {
        viewModelScope.launch(Dispatchers.IO) {
            respEditorial = RetrofitClient.webService.obtenerEditoriales()
            withContext(Dispatchers.Main) {
                _listaEditoriales.value = respEditorial.body()!!.data
            }
        }
    }

    fun spinnerCategoria() {
        viewModelScope.launch(Dispatchers.IO) {
            respCategoria = RetrofitClient.webService.obtenerCategorias()
            withContext(Dispatchers.Main) {
                _listaCategorias.value = respCategoria.body()!!.data
            }
        }
    }
    // Funciones de Validación.
    fun validarCamposPrestamo(
        libro: DataPrestamo
    ): Boolean {
        return if(
            libro.idPresta.isEmpty() ||
            libro.isbn.isEmpty() ||
            libro.idUsuario.isEmpty() ||
            libro.fechaPrestamo.isEmpty()
        ) {
            false
        } else {
            true
        }
    }

    fun validarCampos(
        isbn: String,
        nomLibro: String,
        descripcion: String,
        anioPublicacion: String,
        autorSeleccionado: String,
        edicion: String,
        editorialSeleccionado: String,
        categoriaSeleccionado: String,
        existencias: String
    ): Boolean {
        return if(
            isbn.isEmpty() ||
            nomLibro.isEmpty() ||
            descripcion.isEmpty() ||
            anioPublicacion.isEmpty() ||
            autorSeleccionado.isEmpty() ||
            edicion.isEmpty() ||
            editorialSeleccionado.isEmpty() ||
            categoriaSeleccionado.isEmpty() ||
            existencias.isEmpty()
        ) {
            false
        } else {
            true
        }
    }

}
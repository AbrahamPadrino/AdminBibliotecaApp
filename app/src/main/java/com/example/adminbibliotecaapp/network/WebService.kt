package com.example.adminbibliotecaapp.network

import com.example.adminbibliotecaapp.models.DataAdminUsuario
import com.example.adminbibliotecaapp.models.DataAutor
import com.example.adminbibliotecaapp.models.DataCategoria
import com.example.adminbibliotecaapp.models.DataEditorial
import com.example.adminbibliotecaapp.models.DataLibro
import com.example.adminbibliotecaapp.models.DataPrestamo
import com.example.adminbibliotecaapp.models.DataUsuario
import com.example.adminbibliotecaapp.response.AdminUsuarioResponse
import com.example.adminbibliotecaapp.response.AutorResponse
import com.example.adminbibliotecaapp.response.CategoriaResponse
import com.example.adminbibliotecaapp.response.EditorialResponse
import com.example.adminbibliotecaapp.response.LibroResponse
import com.example.adminbibliotecaapp.response.PrestamoResponse
import com.example.adminbibliotecaapp.response.UsuarioResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WebService {

    @POST("/adminlogin")
    suspend fun login(
        @Body usuario: DataAdminUsuario
    ): Response<AdminUsuarioResponse>

    @GET("/usuarios")
    suspend fun obtenerUsuarios()
    : Response<UsuarioResponse>

    @POST("/usuarios/add")
    suspend fun agregarUsuario(
        @Body usuario: DataUsuario
    ): Response<UsuarioResponse>

    @POST("/usuarios/update")
    suspend fun actualizarUsuario(
        @Body usuario: DataUsuario
    ): Response<UsuarioResponse>

    @POST("/usuarios/delete")
    suspend fun borrarUsuario(
        @Body usuario: DataUsuario
    ): Response<UsuarioResponse>

    // AUTORES
    @GET("/autores")
    suspend fun obtenerAutores()
    : Response<AutorResponse>

    @POST("autores/add")
    suspend fun agregarAutor(
        @Body autor: DataAutor
    ): Response<AutorResponse>

    @POST("/autores/update")
    suspend fun actualizarAutor(
        @Body autor: DataAutor
    ): Response<AutorResponse>

    @POST("/autores/delete")
    suspend fun borrarAutor(
        @Body autor: DataAutor
    ): Response<AutorResponse>
    // END AUTORES

    // EDITORIALES
    @GET("/editoriales")
    suspend fun obtenerEditoriales()
    : Response<EditorialResponse>

    @POST("/editoriales/add")
    suspend fun agregarEditorial(
        @Body editorial: DataEditorial
    ): Response<EditorialResponse>

    @POST("/editoriales/update")
    suspend fun actualizarEditorial(
        @Body editorial: DataEditorial
    ): Response<EditorialResponse>

    @POST("/editoriales/delete")
    suspend fun borrarEditorial(
        @Body editorial: DataEditorial
    ): Response<EditorialResponse>
    // END EDITORIALES


    // CATEGORIAS
    @GET("/categorias")
    suspend fun obtenerCategorias()
    : Response<CategoriaResponse>

    @POST("/categorias/add")
    suspend fun agregarCategoria(
        @Body categoria: DataCategoria
    ): Response<CategoriaResponse>

    @POST("/categorias/update")
    suspend fun actualizarCategoria(
        @Body categoria: DataCategoria
    ): Response<CategoriaResponse>

    @POST("/categorias/delete")
    suspend fun borrarCategoria(
        @Body categoria: DataCategoria
    ): Response<CategoriaResponse>
    // END CATEGORIAS

    // LIBROS
    @GET("/libros")
    suspend fun obtenerLibros()
    : Response<LibroResponse>

    @POST("/libros/add")
    suspend fun agregarLibro(
        @Body libro: DataLibro
    ): Response<LibroResponse>

    @POST("/libros/update")
    suspend fun actualizarLibro(
        @Body libro: DataLibro
    ): Response<LibroResponse>

    @POST("/libros/delete")
    suspend fun borrarLibro(
        @Body libro: DataLibro
    ): Response<LibroResponse>
    // END LIBROS

    // PRESTAMOS
    @GET("/prestamos")
    suspend fun obtenerPrestamos()
    : Response<PrestamoResponse>

    @POST("/prestamos/add")
    suspend fun agregarPrestamo(
        @Body prestamo: DataPrestamo
    ): Response<PrestamoResponse>

    @POST("/prestamos/delete")
    suspend fun borrarPrestamo(
        @Body prestamo: DataPrestamo
    ): Response<PrestamoResponse>
    // END PRESTAMOS

}
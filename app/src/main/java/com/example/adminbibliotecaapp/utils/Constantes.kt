package com.example.adminbibliotecaapp.utils

object Constantes {
    const val BASE_URL = "http://192.168.18.20:3000"
    const val URL_ICON_MENU = "${BASE_URL}/static/icon_menu/"
    const val URL_PORTADAS = "${BASE_URL}/static/portadas_libros/"

    val listMenu = listOf(
        "usuarios",
        "autores",
        "editoriales",
        "categorias",
        "libros",
        "prestamos"
    )
}
package com.example.adminbibliotecaapp.navigation

sealed class Views(
    val route: String
) {
    object SplashView: Views("splash_screen")
    object InicioView: Views("inicio")
    object MenuView: Views("menu")
    object UsuariosView: Views("usuarios")
    object AutoresView: Views("autores")
    object EditorialesView: Views("editoriales")
    object CategoriasView: Views("categorias")
    object LibrosView: Views("libros")
    object PrestamosView: Views("prestamos")
}
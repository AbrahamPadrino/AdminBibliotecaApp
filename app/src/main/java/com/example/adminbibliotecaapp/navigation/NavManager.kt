package com.example.adminbibliotecaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.adminbibliotecaapp.viewmodels.BibliotecaViewModel
import com.example.adminbibliotecaapp.views.AutoresView
import com.example.adminbibliotecaapp.views.CategoriasView
import com.example.adminbibliotecaapp.views.EditorialesView
import com.example.adminbibliotecaapp.views.LibrosView
import com.example.adminbibliotecaapp.views.LoginView
import com.example.adminbibliotecaapp.views.MenuView
import com.example.adminbibliotecaapp.views.PrestamosView
import com.example.adminbibliotecaapp.views.SplashView
import com.example.adminbibliotecaapp.views.UsuariosView

@Composable
fun NavManager(
    viewModel: BibliotecaViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Views.SplashView.route
    ) {
        composable(Views.SplashView.route) {
            SplashView(navController = navController)
        }

        composable(Views.InicioView.route) {
            LoginView(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(Views.MenuView.route) {
            MenuView(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(Views.UsuariosView.route) {
            UsuariosView()
        }

        composable(Views.AutoresView.route) {
            AutoresView()
        }

        composable(Views.EditorialesView.route) {
            EditorialesView()
        }

        composable(Views.CategoriasView.route) {
            CategoriasView()
        }

        composable(Views.LibrosView.route) {
            LibrosView()
        }

        composable(Views.PrestamosView.route) {
            PrestamosView()
        }
    }
}
package com.example.adminbibliotecaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.adminbibliotecaapp.navigation.NavManager
import com.example.adminbibliotecaapp.network.RetrofitClient
import com.example.adminbibliotecaapp.ui.theme.AdminBibliotecaAppTheme
import com.example.adminbibliotecaapp.viewmodels.BibliotecaViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdminBibliotecaAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // inicializar la instancia de retrofit
                    val retrofit = RetrofitClient.webService

                    // inicializar el viewmodel
                    val viewModel: BibliotecaViewModel = viewModel()
                    //val viewModel = BibliotecaViewModel()

                    // instanciar el administrador de navegación
                    NavManager(viewModel = viewModel)

                }
            }
        }
    }
}
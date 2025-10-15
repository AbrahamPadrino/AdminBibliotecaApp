package com.example.adminbibliotecaapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.adminbibliotecaapp.R
import com.example.adminbibliotecaapp.navigation.Views
import com.example.adminbibliotecaapp.utils.Constantes
import com.example.adminbibliotecaapp.viewmodels.BibliotecaViewModel

@Composable
fun MenuView(
    navController: NavController,
    viewModel: BibliotecaViewModel
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.popBackStack()
                    viewModel.logout()
                    navController.navigate(Views.InicioView.route)
                },
                containerColor = colorResource(id = R.color.gris_oscuro)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End

    ) {
        MenuScreen(
            it,
            navController = navController
        )

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuScreen(
    it: PaddingValues,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(Constantes.listMenu) {
                Card(
                    modifier = Modifier
                        .padding(12.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    onClick = {
                        when(it) {
                            "usuarios" -> navController.navigate(Views.UsuariosView.route)
                            "autores" -> navController.navigate(Views.AutoresView.route)
                            "editoriales" -> navController.navigate(Views.EditorialesView.route)
                            "categorias" -> navController.navigate(Views.CategoriasView.route)
                            "libros" -> navController.navigate(Views.LibrosView.route)
                            "prestamos" -> navController.navigate(Views.PrestamosView.route)
                        }
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        GlideImage(
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .padding(12.dp),
                            model = "${Constantes.URL_ICON_MENU}${it}.png",
                            contentDescription = null,
                            contentScale = ContentScale.Inside
                        )
                        Text(
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            text = it
                        )
                    }
                }
            }
        }
    }
}
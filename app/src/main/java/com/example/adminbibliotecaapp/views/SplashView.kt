package com.example.adminbibliotecaapp.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.adminbibliotecaapp.R
import com.example.adminbibliotecaapp.navigation.Views
import kotlinx.coroutines.delay

@Composable
fun SplashView(
    navController: NavController
) {
    LaunchedEffect(key1 = true ) {
        delay(3000)
        navController.popBackStack()    // LIMPIA LA PANTALLA
        navController.navigate(Views.InicioView.route)  // NAVEGA A LA VISTA DE LOGIN
    }

    SplashScreen()
}
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.azul_100)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GlideImage(
            model =R.drawable.gif_libro,
            contentDescription = "gif_logo"

        )
    }
}
package com.example.adminbibliotecaapp.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adminbibliotecaapp.viewmodels.PrestamoViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun PrestamosView() {

    val viewModel = PrestamoViewModel()

    PrestamosScreen(viewModel)

}

@Composable
fun PrestamosScreen(
    viewModel: PrestamoViewModel
) {
    // Estado para listar los prestamos y otro para filtrar mediante una busqueda.
    val listaPrestamos by viewModel.listaPrestamos.collectAsState()
    var textSearch by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        // Campo de Busqueda de prestamos.
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 8.dp),
            value = textSearch,
            onValueChange = {
                textSearch = it
            },
            placeholder = {
                Text(text = "Buscar por ID usuario")
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 20.sp
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        // Lista de prestamos.
        LazyColumn(
            modifier = Modifier
                .padding(4.dp)
        ) {
            items(
                listaPrestamos.filter {
                    it.idUsuario.toLowerCase().contains(textSearch)
                }
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(text = "ID PRESTAMO: ")
                        Text(text = it.idPresta)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(text = "ID USUARIO: ")
                        Text(text = it.idUsuario)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(text = "FECHA PRESTAMO: ")
                        Text(text = it.fechaPrestamo)
                    }

                    Button(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            viewModel.devolverPrestamo(it)
                        }
                    ) {
                        Text(text = "Devolver libro")
                    }
                }
            }
        }
    }
}

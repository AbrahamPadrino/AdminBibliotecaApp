package com.example.adminbibliotecaapp.views

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.adminbibliotecaapp.R
import com.example.adminbibliotecaapp.models.DataCategoria
import com.example.adminbibliotecaapp.viewmodels.CategoriaViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoriasView() {
// 1 - Crear los estados de la UI.
    val openDialog =
        remember { mutableStateOf(false) }    // Mostrar/Ocultar un dialogo para agregar o editar la información de una categoria.
    val categoriaAddUpdate =
        remember { mutableStateOf("") } // Comparar los valores dependiendo de la acción que se desea realizar (Agregar o Editar).
    val viewModel: CategoriaViewModel = viewModel() // Instanciar el viewModel
    val categoriaEdit =
        remember { mutableStateOf(DataCategoria()) } // Cambia la información del objeto según sea el caso (Agregar o Editar) para enviarla al servidor.

// 2 - Poner el lienzo Scaffold (Contenedor) para los elementos de la UI.
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    categoriaAddUpdate.value = "add"
                    openDialog.value = true
                },
                containerColor = colorResource(id = R.color.morado_oscuro),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        if (openDialog.value) {
            DialogAddEditCategoria(
                categoriaAddUpdate = categoriaAddUpdate,
                openDialog = openDialog,
                viewModel = viewModel,
                categoriaEdit = categoriaEdit
            )
        }

        CategoriasScreen(
            categoriaAddUpdate = categoriaAddUpdate,
            openDialog = openDialog,
            viewModel = viewModel,
            categoriaEdit = categoriaEdit
        )

    }
}

@Composable
fun CategoriasScreen(
    categoriaAddUpdate: MutableState<String>,
    openDialog: MutableState<Boolean>,
    viewModel: CategoriaViewModel,
    categoriaEdit: MutableState<DataCategoria>

) {
    val listaCategorias by viewModel.listaCategorias.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(4.dp)
        ) {
            items(listaCategorias) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .weight(2f),
                            text = it.nomCategoria
                        )

                        IconButton(
                            onClick = {
                                categoriaAddUpdate.value = "edit"
                                openDialog.value = true
                                categoriaEdit.value = it
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "editar"
                            )
                        }

                        IconButton(
                            onClick = {
                                viewModel.borrarCategoria(categoria = it)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "borrar"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DialogAddEditCategoria(
    categoriaAddUpdate: MutableState<String>,
    openDialog: MutableState<Boolean>,
    viewModel: CategoriaViewModel,
    categoriaEdit: MutableState<DataCategoria>

) {
    val context = LocalContext.current

    var nomCategoria by remember { mutableStateOf("") }

    if (categoriaAddUpdate.value == "edit") {
        nomCategoria = categoriaEdit.value.nomCategoria
    }

    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            if (categoriaAddUpdate.value == "add") {
                Text(text = "Agregar Categoria")
            } else if (categoriaAddUpdate.value == "edit") {
                Text(text = "Editar Categoria")
            }
        },
        text = {
            Column {
                OutlinedTextField(
                    value = nomCategoria,
                    onValueChange = {
                        nomCategoria = it
                    },
                    label = {
                        Text(text = "Nombre de la Categoria")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }
        },
        // Botón para aceptar la acción
        confirmButton = {
            Button(
                onClick = {
                    if (categoriaAddUpdate.value == "add") {
                        categoriaEdit.value.idCategoria = System.currentTimeMillis().toString()
                        categoriaEdit.value.nomCategoria = nomCategoria
                    } else if (categoriaAddUpdate.value == "edit") {
                        categoriaEdit.value.nomCategoria = nomCategoria
                    }

                    if (viewModel.validarCampos(categoriaEdit)) {
                        if (categoriaAddUpdate.value == "add") {
                            viewModel.agregarCategoria(categoriaEdit.value)
                        } else if (categoriaAddUpdate.value == "edit") {
                            viewModel.editarCategoria(categoriaEdit.value)
                        }
                    } else {
                        Toast.makeText(context, "Debes llenar todos los campos", Toast.LENGTH_LONG)
                            .show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.verde_oscuro)
                )
            ) {
                Text(text = "Aceptar")
            }
        },
        // Botón para cancelar la acción
        dismissButton = {
            Button(
                onClick = {
                    openDialog.value = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.rojo_oscuro)
                )
            ) {
                Text(text = "Cancelar")
            }
        }
    )
}
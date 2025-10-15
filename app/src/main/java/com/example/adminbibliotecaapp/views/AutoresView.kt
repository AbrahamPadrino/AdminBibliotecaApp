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
import com.example.adminbibliotecaapp.models.DataAutor
import com.example.adminbibliotecaapp.viewmodels.AutorViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AutoresView() {
    val openDialog = remember { mutableStateOf(false) }    // Mostrar/Ocultar un dialogo para agregar o editar la información de un autor.
    val autorAddUpdate = remember { mutableStateOf("") }    // Comparar los valores dependiendo de la acción que se desea realizar.
    val viewModel: AutorViewModel = viewModel()   // Instanciar el viewModel
    // val viewModel = AutorViewModel()
    val autorEdit = remember { mutableStateOf(DataAutor()) }    // Cambia la información del objeto según sea el caso.

    // Poner el lienzo Scaffold
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    autorAddUpdate.value = "add"
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
            DialogAddEditAutor(
                autorAddUpdate = autorAddUpdate,
                openDialog = openDialog,
                viewModel = viewModel,
                autorEdit = autorEdit
            )
        }

        AutoresScreen(
            autorAddUpdate = autorAddUpdate,
            openDialog = openDialog,
            viewModel = viewModel,
            autorEdit = autorEdit
        )

    }
}

@Composable
fun AutoresScreen(
    autorAddUpdate: MutableState<String>,
    openDialog: MutableState<Boolean>,
    viewModel: AutorViewModel,
    autorEdit: MutableState<DataAutor>
) {
    val listaAutores by viewModel.listaAutores.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        LazyColumn(
            modifier =  Modifier
                .padding(4.dp)
        ) {
            items(listaAutores) {
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
                            text = it.nomAutor
                        )

                        IconButton(
                            onClick = {
                                autorAddUpdate.value = "edit"
                                openDialog.value = true
                                autorEdit.value = it
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "editar autor")
                        }

                        IconButton(
                            onClick = {
                                viewModel.borrarAutor(autor = it)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "borrar autor")
                        }
                    }
                }
            }
        }
    }
}

// Dialogo para agregar o editar un autor
@Composable
fun DialogAddEditAutor(
    autorAddUpdate: MutableState<String>,
    openDialog: MutableState<Boolean>,
    viewModel: AutorViewModel,
    autorEdit: MutableState<DataAutor>

) {
    val context = LocalContext.current

    var nomAutor by remember { mutableStateOf("") }

    if (autorAddUpdate.value == "edit") {
        nomAutor = autorEdit.value.nomAutor
    }

    AlertDialog(
        title = {
            if (autorAddUpdate.value == "add") {
                Text(text = "Agregar Autor")
            } else if(autorAddUpdate.value == "edit") {
                Text(text = "Editar Autor")
            }
        },
        text = {
            Column {
                OutlinedTextField(
                    value = nomAutor,
                    onValueChange = {
                        nomAutor = it
                    },
                    label = {
                        Text(text = "Nombre del autor")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }
        },
        onDismissRequest = {
            openDialog.value = false
        },
        confirmButton = {
            Button(
                onClick = {
                    if (autorAddUpdate.value == "add") {
                        autorEdit.value.idAutor = System.currentTimeMillis().toString()
                        autorEdit.value.nomAutor = nomAutor
                    } else if(autorAddUpdate.value == "edit") {
                        autorEdit.value.nomAutor = nomAutor
                    }

                    if (viewModel.validarCampos(autorEdit)) {
                        if (autorAddUpdate.value == "add") {
                            viewModel.agregarAutor(autor = autorEdit.value)
                        } else if(autorAddUpdate.value == "edit") {
                            viewModel.editarAutor(autor = autorEdit.value)
                        }
                        openDialog.value = false
                    } else {
                        Toast.makeText(context, "Debes llenar todos los campos", Toast.LENGTH_LONG).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.verde_oscuro)
                )
            ) {
                Text(text = "Aceptar")
            }
        },
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


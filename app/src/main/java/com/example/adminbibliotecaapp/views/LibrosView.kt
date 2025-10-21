package com.example.adminbibliotecaapp.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.adminbibliotecaapp.R
import com.example.adminbibliotecaapp.models.DataLibro
import com.example.adminbibliotecaapp.models.DataPrestamo
import com.example.adminbibliotecaapp.utils.Constantes
import com.example.adminbibliotecaapp.viewmodels.LibroViewModel
import java.time.format.DateTimeFormatter

@SuppressLint("ViewModelConstructorInComposable", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LibrosView() {
    // Crea los estados
    val openDialog = remember { mutableStateOf(false) }
    val openDialogPrestar = remember { mutableStateOf(false) }
    val libroAddUpdate = remember { mutableStateOf("") }
    val viewModel = LibroViewModel()
    val libroEdit = remember { mutableStateOf(DataLibro()) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    libroAddUpdate.value = "add"
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
            DialogAddEditLibro(
                libroAddUpdate = libroAddUpdate,
                openDialog = openDialog,
                viewModel = viewModel,
                libroEdit = libroEdit
            )
        }

        LibrosScreen(
            libroAddUpdate = libroAddUpdate,
            openDialog = openDialog,
            openDialogPrestar = openDialogPrestar,
            viewModel = viewModel,
            libroEdit = libroEdit
        )
    }
}
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LibrosScreen (
    libroAddUpdate: MutableState<String>,
    openDialog: MutableState<Boolean>,
    openDialogPrestar: MutableState<Boolean>,
    viewModel: LibroViewModel,
    libroEdit: MutableState<DataLibro>
) {
    val listaLibros by viewModel.listaLibros.collectAsState()
    var textSearch by remember { mutableStateOf("") }

    if (openDialogPrestar.value) {
        DialogAddPrestar(
            openDialogPrestar = openDialogPrestar,
            viewModel = viewModel,
            libroEdit = libroEdit
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 8.dp),
            value = textSearch,
            onValueChange = {
                textSearch = it
            },
            placeholder = {
                Text(text = "ISBN ó Libro a buscar")
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
        //
        LazyVerticalGrid(
            modifier = Modifier
                .padding(4.dp),
            columns = GridCells.Fixed(2)
        ) {
            items(
                listaLibros.filter {
                    it.nomLibro.lowercase().contains(textSearch.lowercase().trim()) ||
                            it.isbn.lowercase().contains(textSearch.lowercase().trim())
                }
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    GlideImage(
                        model = "${Constantes.URL_PORTADAS}${it.portada}",
                        contentScale = ContentScale.Fit,
                        contentDescription = "portada"
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = it.nomLibro,
                            style = TextStyle(
                                fontSize = 16.sp
                            )
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                openDialogPrestar.value = true
                                libroEdit.value = it
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = null
                            )
                        }

                        IconButton(
                            onClick = {
                                libroAddUpdate.value = "edit"
                                openDialog.value = true
                                libroEdit.value = it
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "editar"
                            )
                        }

                        IconButton(
                            onClick = {
                                viewModel.borrarLibro(it)
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
        //
    }
}

@Composable
fun DialogAddPrestar(
    openDialogPrestar: MutableState<Boolean>,
    viewModel: LibroViewModel,
    libroEdit: MutableState<DataLibro>
) {
    var idUsuario by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {
            openDialogPrestar.value = false
        },
        title = {
            Text(text = "Prestar Libro")
        },
        text = {
            Column {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    value = libroEdit.value.isbn,
                    onValueChange = { },
                    label = {
                        Text(text = "ISBN")
                    },
                    readOnly = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    value = libroEdit.value.nomLibro,
                    onValueChange = { },
                    label = {
                        Text(text = "LIBRO")
                    },
                    readOnly = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    value = idUsuario,
                    onValueChange = {
                        idUsuario = it
                    },
                    label = {
                        Text(text = "ID Usuario a prestar")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val fechaPrestamo = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val dataPrestamo = DataPrestamo(
                        idPresta = System.currentTimeMillis().toString(),
                        isbn = libroEdit.value.isbn,
                        idUsuario = idUsuario,
                        fechaPrestamo = fechaPrestamo.toString()
                    )

                    if (viewModel.validarCamposPrestamo(dataPrestamo)) {
                        viewModel.prestarLibro(dataPrestamo)
                    }

                    openDialogPrestar.value = false
                }
            ) {
                Text(text = "Prestar")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    openDialogPrestar.value = false
                }
            ) {
                Text(text = "Cancelar")
            }
        }
    )
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogAddEditLibro(
    libroAddUpdate: MutableState<String>,
    openDialog: MutableState<Boolean>,
    viewModel: LibroViewModel,
    libroEdit: MutableState<DataLibro>
) {
    val keyboardController = LocalSoftwareKeyboardController.current // Para ocultar el teclado.

    var showAutor by remember { mutableStateOf(false) }
    var autorSeleccionado by remember { mutableStateOf("Selecciona un autor") }

    var showEditorial by remember { mutableStateOf(false) }
    var editorialSeleccionado by remember { mutableStateOf("Selecciona una editorial") }

    var showCategoria by remember { mutableStateOf(false) }
    var categoriaSeleccionada by remember { mutableStateOf("Selecciona una categoria") }

    var isbn by remember { mutableStateOf("") }
    var nomLibro by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var anioPublicacion by remember { mutableStateOf("") }
    var edicion by remember { mutableStateOf("") }
    var existencias by remember { mutableStateOf("") }

    if (libroAddUpdate.value == "edit") {
        isbn = libroEdit.value.isbn
        nomLibro = libroEdit.value.nomLibro
        descripcion = libroEdit.value.descripcion
        anioPublicacion = libroEdit.value.anioPublicacion
        edicion = libroEdit.value.edicion
        existencias = libroEdit.value.existencias.toString()
        autorSeleccionado = libroEdit.value.autor
        editorialSeleccionado = libroEdit.value.editorial
        categoriaSeleccionada = libroEdit.value.categoria
    }

    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            if (libroAddUpdate.value == "add") {
                Text(text = "Agregar Libro")
            } else if (libroAddUpdate.value == "edit") {
                Text(text = "Editar Libro")
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = isbn,
                    onValueChange = {
                        isbn = it
                    },
                    label = {
                        Text(text = "ISBN")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                OutlinedTextField(
                    value = nomLibro,
                    onValueChange = {
                        nomLibro = it
                    },
                    label = {
                        Text(text = "Nombre del Libro")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                OutlinedTextField(
                    value = anioPublicacion,
                    onValueChange = {
                        anioPublicacion = it
                    },
                    label = {
                        Text(text = "Año publicación")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    expanded = showAutor,
                    onExpandedChange = {
                        showAutor = !showAutor
                    }
                ) {
                    keyboardController?.hide()

                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(),
                        value = autorSeleccionado,
                        onValueChange = { },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showAutor)},
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = showAutor,
                        onDismissRequest = { showAutor = false }
                    ) {
                        viewModel.listaAutores.value.forEachIndexed { index, s ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = s.toString())
                                },
                                onClick = {
                                    if (s.toString() != "") {
                                        autorSeleccionado = s.nomAutor
                                    }
                                    showAutor = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    expanded = showEditorial,
                    onExpandedChange = {
                        showEditorial = !showEditorial
                    }
                ) {
                    keyboardController?.hide()

                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(),
                        value = editorialSeleccionado,
                        onValueChange = { },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showEditorial)},
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = showEditorial,
                        onDismissRequest = { showEditorial = false }
                    ) {
                        viewModel.listaEditoriales.value.forEachIndexed { index, s ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = s.toString())
                                },
                                onClick = {
                                    if (s.toString() != "") {
                                        editorialSeleccionado = s.nomEditorial
                                    }
                                    showEditorial = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    expanded = showCategoria,
                    onExpandedChange = {
                        showCategoria = !showCategoria
                    }
                ) {
                    keyboardController?.hide()

                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(),
                        value = categoriaSeleccionada,
                        onValueChange = { },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showCategoria)},
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = showCategoria,
                        onDismissRequest = { showCategoria = false }
                    ) {
                        viewModel.listaCategorias.value.forEachIndexed { index, s ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = s.toString())
                                },
                                onClick = {
                                    if (s.toString() != "") {
                                        categoriaSeleccionada = s.nomCategoria
                                    }
                                    showCategoria = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = {
                        descripcion = it
                    },
                    label = {
                        Text(text = "Descripción")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                OutlinedTextField(
                    value = edicion,
                    onValueChange = {
                        edicion = it
                    },
                    label = {
                        Text(text = "Edición")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    value = existencias,
                    onValueChange = {
                        existencias = it
                    },
                    label = {
                        Text(text = "Existencias")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (
                        viewModel.validarCampos(
                            isbn,
                            nomLibro,
                            descripcion,
                            anioPublicacion,
                            autorSeleccionado,
                            edicion,
                            editorialSeleccionado,
                            categoriaSeleccionada,
                            existencias
                        )
                    ) {
                        val libro = DataLibro(
                            isbn = isbn,
                            nomLibro = nomLibro,
                            portada = "${isbn}.png",
                            descripcion = descripcion,
                            anioPublicacion = anioPublicacion,
                            autor = autorSeleccionado,
                            edicion = edicion,
                            editorial = editorialSeleccionado,
                            categoria = categoriaSeleccionada,
                            existencias = existencias.toInt()
                        )

                        if (libroAddUpdate.value == "add") {
                            viewModel.agregarLibro(libro)
                        } else {
                            viewModel.editarLibro(libro)
                        }

                        openDialog.value = false
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
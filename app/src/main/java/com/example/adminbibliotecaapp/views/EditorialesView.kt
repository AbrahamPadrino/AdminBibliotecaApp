package com.example.adminbibliotecaapp.views

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.example.adminbibliotecaapp.R
import com.example.adminbibliotecaapp.models.DataEditorial
import com.example.adminbibliotecaapp.viewmodels.EditorialViewModel

@SuppressLint("ViewModelConstructorInComposable", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditorialesView() {
    val openDialog = remember { mutableStateOf(false) }
    val editorialAddUpdate = remember { mutableStateOf("") }
    val viewModel = EditorialViewModel()
    val editorialEdit = remember { mutableStateOf(DataEditorial()) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editorialAddUpdate.value = "add"
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
            DialogAddEditEditorial(
                editorialAddUpdate = editorialAddUpdate,
                openDialog = openDialog,
                viewModel = viewModel,
                editorialEdit = editorialEdit
            )
        }

        EditorialesScreen(
            editorialAddUpdate = editorialAddUpdate,
            openDialog = openDialog,
            viewModel = viewModel,
            editorialEdit = editorialEdit
        )
    }
}

@Composable
fun EditorialesScreen(
    editorialAddUpdate: MutableState<String>,
    openDialog: MutableState<Boolean>,
    viewModel: EditorialViewModel,
    editorialEdit: MutableState<DataEditorial>
) {
    val listaEditoriales by viewModel.listaEditoriales.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(4.dp)
        ) {
            items(listaEditoriales) {
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
                            text = it.nomEditorial
                        )

                        IconButton(
                            onClick = {
                                editorialAddUpdate.value = "edit"
                                openDialog.value = true
                                editorialEdit.value = it
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar Editorial"
                            )
                        }

                        IconButton(
                            onClick = {
                                viewModel.borrarEditorial(editorial = it)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Borrar Editorial"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DialogAddEditEditorial(
    editorialAddUpdate: MutableState<String>,
    openDialog: MutableState<Boolean>,
    viewModel: EditorialViewModel,
    editorialEdit: MutableState<DataEditorial>
) {
    val context = LocalContext.current

    var nomEditorial by remember { mutableStateOf("") }

    if (editorialAddUpdate.value == "edit") {
        nomEditorial = editorialEdit.value.nomEditorial
    }

    val scrollState = rememberScrollState()

    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            if (editorialAddUpdate.value == "add") {
                Text(text = "Agregar Editorial")
            } else if (editorialAddUpdate.value == "edit") {
                Text(text = "Editar Editorial")
            }
        },
        text = {
            FlowColumn(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                OutlinedTextField(
                    value = nomEditorial,
                    onValueChange = {
                        nomEditorial = it
                    },
                    label = {
                        Text(text = "Nombre de la Editorial")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (editorialAddUpdate.value == "add") {
                        editorialEdit.value.idEditorial = System.currentTimeMillis().toString()
                        editorialEdit.value.nomEditorial = nomEditorial
                    } else if(editorialAddUpdate.value == "edit") {
                        editorialEdit.value.nomEditorial = nomEditorial
                    }

                    if (viewModel.validarCampos(editorialEdit)) {
                        if (editorialAddUpdate.value == "add") {
                            viewModel.agregarEditorial(editorial = editorialEdit.value)
                        } else if (editorialAddUpdate.value == "edit") {
                            viewModel.editarEditorial(editorialEdit.value)
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
                Text("Aceptar")
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
                Text("Cancelar")
            }
        }
    )
}
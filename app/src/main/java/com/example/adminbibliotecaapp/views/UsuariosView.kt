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
import com.example.adminbibliotecaapp.models.DataUsuario
import com.example.adminbibliotecaapp.viewmodels.UsuarioViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UsuariosView() {
    val openDialog = remember { mutableStateOf(false) }
    val usuarioAddUpdate = remember { mutableStateOf("") }
    val viewModel: UsuarioViewModel = viewModel()
    val usuarioEdit = remember { mutableStateOf(DataUsuario()) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    usuarioAddUpdate.value = "add"
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
            DialogAddEditUsuario(
                usuarioAddUpdate = usuarioAddUpdate,
                openDialog = openDialog,
                viewModel = viewModel,
                usuarioEdit = usuarioEdit
            )
        }

        UsuariosScreen(
            usuarioAddUpdate,
            openDialog,
            viewModel,
            usuarioEdit
        )
    }
}

@Composable
fun UsuariosScreen(
    usuarioAddUpdate: MutableState<String>,
    openDialog: MutableState<Boolean>,
    viewModel: UsuarioViewModel,
    usuarioEdit: MutableState<DataUsuario>
) {
    val listaUsuarios by viewModel.listaUsuarios.collectAsState()
    var colorEstado by remember { mutableStateOf(R.color.verde_oscuro) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(4.dp)
        ) {
            items(listaUsuarios) {

                colorEstado = if (it.estadoUsuario.isNullOrEmpty()) {
                    R.color.verde_oscuro
                } else {
                    R.color.rojo_oscuro
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = colorEstado),
                        contentColor = Color.White
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
                            text = it.nomUsuario
                        )

                        IconButton(
                            onClick = {
                                usuarioAddUpdate.value = "edit"
                                openDialog.value = true
                                usuarioEdit.value = it
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar Usuario"
                            )
                        }

                        IconButton(
                            onClick = {
                                viewModel.borrarUsuario(usuario = it)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Borrar Usuario"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DialogAddEditUsuario(
    usuarioAddUpdate: MutableState<String>,
    openDialog: MutableState<Boolean>,
    viewModel: UsuarioViewModel,
    usuarioEdit: MutableState<DataUsuario>
) {

    val context = LocalContext.current

    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    if (usuarioAddUpdate.value == "edit") {
        usuario = usuarioEdit.value.nomUsuario
        contrasena = usuarioEdit.value.contrasena
    }

    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            if (usuarioAddUpdate.value == "add") {
                Text(text = "Agregar Usuario")
            } else if (usuarioAddUpdate.value == "edit") {
                Text(text = "Editar Usuario")
            }
        },
        text = {
            Column {
                OutlinedTextField(
                    value = usuario,
                    onValueChange = {
                        usuario = it
                    },
                    label = {
                        Text(text = "Nombre de usuario")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                OutlinedTextField(
                    value = contrasena,
                    onValueChange = {
                        contrasena = it
                    },
                    label = {
                        Text(text = "Contraseña")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (usuarioAddUpdate.value == "add") {
                        usuarioEdit.value.idUsuario = System.currentTimeMillis().toString()
                        usuarioEdit.value.nomUsuario = usuario
                        usuarioEdit.value.contrasena = contrasena
                    } else if (usuarioAddUpdate.value == "edit") {
                        usuarioEdit.value.nomUsuario = usuario
                        usuarioEdit.value.contrasena = contrasena
                    }

                    if (viewModel.validarCampos(usuarioEdit)) {
                        if (usuarioAddUpdate.value == "add") {
                            viewModel.agregarUsuario(usuario = usuarioEdit.value)
                        } else if (usuarioAddUpdate.value == "edit") {
                            viewModel.editarUsuario(usuarioEdit.value)
                        }
                        openDialog.value = false
                    } else {
                        Toast.makeText(context, "Debes llenar todos los campos", Toast.LENGTH_LONG)
                            .show()
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
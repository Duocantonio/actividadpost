package com.example.apirest.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.apirest.model.Usuario
import com.example.apirest.viewModel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(viewModel: PostViewModel) {

    val usuarios = viewModel.postList.collectAsState().value
    var usuarioEnEdicion by remember { mutableStateOf<Usuario?>(null) }
    var mensajeError by remember { mutableStateOf("") }

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var clave1 by remember { mutableStateOf("") }
    var clave2 by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Listado de usuarios") })
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))

            TextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))

            TextField(
                value = clave1,
                onValueChange = { clave1 = it },
                label = { Text("Clave1") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            TextField(
                value = clave2,
                onValueChange = { clave2 = it },
                label = { Text("Clave2") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            TextField(value = edad, onValueChange = { edad = it }, label = { Text("Edad") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))

            TextField(value = direccion, onValueChange = { direccion = it }, label = { Text("Dirección") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    if (clave1==clave2){
                        mensajeError = "Las claves no coinciden"
                        return@Button
                    }
                    viewModel.createUsuario(
                        Usuario(
                            nombre = nombre,
                            email = email,
                            edad = edad.toIntOrNull() ?: 0,
                            clave1 = clave1,
                            clave2 = clave2,
                            direccion = direccion
                        )
                    )
                    nombre = ""; email = ""; edad = ""; clave1 = ""; clave2 = ""; direccion = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Crear Usuario") }

            Spacer(Modifier.height(16.dp))

            LazyColumn {
                items(usuarios) { usuario ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Text("Nombre: ${usuario.nombre}")
                            Text("Email: ${usuario.email}")
                            Text("Edad: ${usuario.edad}")
                            Text("Dirección: ${usuario.direccion}")

                            Spacer(Modifier.height(8.dp))

                            Button(
                                onClick = { usuarioEnEdicion = usuario },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Editar")
                            }
                        }
                    }
                }
            }
        }
    }

    // DIALOGO DE EDICIÓN
    if (usuarioEnEdicion != null) {
        EditUsuarioDialog(
            usuario = usuarioEnEdicion!!,
            onDismiss = { usuarioEnEdicion = null },
            onSave = { usuarioActualizado ->
                usuarioActualizado.id?.let { viewModel.updateUsuario(it.toInt(), usuarioActualizado) }
                usuarioEnEdicion = null
            }
        )
    }
}

@Composable
fun EditUsuarioDialog(
    usuario: Usuario,
    onDismiss: () -> Unit,
    onSave: (Usuario) -> Unit
) {
    var nombre by remember { mutableStateOf(usuario.nombre) }
    var email by remember { mutableStateOf(usuario.email) }
    var edad by remember { mutableStateOf(usuario.edad.toString()) }
    var direccion by remember { mutableStateOf(usuario.direccion) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Usuario") },
        text = {
            Column {
                TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
                TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
                TextField(value = edad, onValueChange = { edad = it }, label = { Text("Edad") })
                TextField(value = direccion, onValueChange = { direccion = it }, label = { Text("Dirección") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val actualizado = usuario.copy(
                    nombre = nombre,
                    email = email,
                    edad = edad.toIntOrNull() ?: 0,
                    direccion = direccion
                )
                onSave(actualizado)
            }) { Text("Guardar") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}



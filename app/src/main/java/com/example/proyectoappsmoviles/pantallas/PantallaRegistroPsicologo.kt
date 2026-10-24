package com.example.proyectoappsmoviles.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyectoappsmoviles.datos.Usuario
import com.example.proyectoappsmoviles.datos.UsuarioDao
import kotlinx.coroutines.launch

@Composable
fun PantallaRegistroPsicologo(
    usuarioDao: UsuarioDao,
    onRegistroExitoso: () -> Unit,
    onVolver: () -> Unit
) {
    var nombreCompleto by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registro Profesional PIE",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = nombreCompleto,
                onValueChange = { nombreCompleto = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (nombreCompleto.isNotBlank() && correo.isNotBlank() && contrasena.isNotBlank()) {
                        scope.launch {
                            usuarioDao.registrarUsuario(
                                Usuario(
                                    nombreCompleto = nombreCompleto,
                                    correo = correo,
                                    contrasena = contrasena
                                )
                            )
                            onRegistroExitoso()
                        }
                    } else {
                        mensaje = "Completa todos los campos"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Usuario")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }
            if (mensaje.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = mensaje)
            }
        }
    }
}
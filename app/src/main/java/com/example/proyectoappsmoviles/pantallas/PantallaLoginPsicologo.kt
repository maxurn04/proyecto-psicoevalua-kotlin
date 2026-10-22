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
import com.example.proyectoappsmoviles.datos.UsuarioDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PantallaLoginPsicologo(
    usuarioDao: UsuarioDao,
    onLoginExitoso: () -> Unit,
    onIrARegistro: () -> Unit,
    onVolver: () -> Unit
) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mensajeEstado by remember { mutableStateOf("") }
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
                text = "Ingresa como Psicologo",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = usuario,
                onValueChange = { usuario = it },
                label = { Text("Usuario/Nombre") },
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
                    if (usuario.isNotBlank() && contrasena.isNotBlank()) {
                        scope.launch(Dispatchers.IO) {
                            val usuarioEncontrado = usuarioDao.iniciarSesion(usuario, contrasena)
                            if (usuarioEncontrado != null) {
                                mensajeEstado = "BASE DE DATOS CORRECTA! INICIO GOOD"
                                onLoginExitoso()
                            } else {
                                mensajeEstado = "Usuario o contraseña incorrectos"
                            }
                        }
                    } else {
                        mensajeEstado = "Completa todos los campos!!!"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sesion")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onIrARegistro,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarse!")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("vuelve atras")
            }
            if (mensajeEstado.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = mensajeEstado)
            }
        }
    }
}
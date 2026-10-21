package com.example.proyectoappsmoviles.pantallas

import androidx.compose.runtime.Composable
import com.example.proyectoappsmoviles.datos.UsuarioDao


@Composable
fun PantallaRegistroPsicologo(
    usuarioDao: UsuarioDao,
    onRegistroExitoso: () -> Unit,
    onVolver: () -> Unit
)
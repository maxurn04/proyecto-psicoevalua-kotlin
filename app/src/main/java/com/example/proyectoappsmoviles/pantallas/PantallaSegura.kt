package com.example.proyectoappsmoviles.pantallas

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

private var pantallasSeguras = 0

private fun Context.buscarActivity(): Activity? {
    var contexto = this
    while (contexto is ContextWrapper) {
        if (contexto is Activity) return contexto
        contexto = contexto.baseContext
    }
    return null
}

@Composable
fun PantallaSegura() {
    val contexto = LocalContext.current
    DisposableEffect(Unit) {
        val ventana = contexto.buscarActivity()?.window
        pantallasSeguras++
        ventana?.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        onDispose {
            pantallasSeguras--
            if (pantallasSeguras == 0) {
                ventana?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
            }
        }
    }
}
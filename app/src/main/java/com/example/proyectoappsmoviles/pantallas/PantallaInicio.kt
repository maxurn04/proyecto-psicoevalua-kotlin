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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaInicio (
    onIrLoginPsicologo: () -> Unit,
    onIrAccesoEstudiantes: () -> Unit
) {
    Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "PsicoEvalúa",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Bienvenido!",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = onIrLoginPsicologo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Acceso Profesional")
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = onIrAccesoEstudiantes,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Acceso Estudiante (Ingreso PIN)")
            }
        }
    }
}
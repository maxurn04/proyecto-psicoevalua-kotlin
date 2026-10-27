package com.example.proyectoappsmoviles.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.proyectoappsmoviles.R

@Composable
fun PantallaInicio (
    onIrLoginPsicologo: () -> Unit,
    onIrAccesoEstudiantes: () -> Unit
) {
    val colorRojo=Color(0xFF832A2D)
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
            Image(
                painter = painterResource(id = R.drawable.pevex),
                contentDescription = "Logo PsicoEvalúa",
                modifier = Modifier.size(100.dp)
            )
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
                colors=ButtonDefaults.buttonColors(
                    containerColor= colorRojo
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Acceso Profesional", color = Color.White)
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
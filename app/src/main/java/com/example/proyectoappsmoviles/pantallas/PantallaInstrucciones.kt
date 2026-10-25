package com.example.proyectoappsmoviles.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectoappsmoviles.datos.Evaluacion

@Composable
fun PantallaInstrucciones(
    evaluacion: Evaluacion,
    onComenzar: () -> Unit
) {
    val colorRojo = Color(0xFF832A2D)
    val colorRosa = Color(0xFFFBECEC)

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Antes de comenzar",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = evaluacion.nombreInstrumento,
                style = MaterialTheme.typography.titleMedium,
                color = colorRojo,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = colorRosa)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "1. Vas a leer unas frases y elegir la opción que más se parezca a ti.",
                        fontSize = 18.sp,
                        lineHeight = 26.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "2. No hay respuestas buenas ni malas.",
                        fontSize = 18.sp,
                        lineHeight = 26.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "3. Elige solo una opción en cada pregunta.",
                        fontSize = 18.sp,
                        lineHeight = 26.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "4. Responde con calma. Si tienes dudas, pregúntale al psicólogo.",
                        fontSize = 18.sp,
                        lineHeight = 26.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onComenzar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorRojo)
            ) {
                Text("Comenzar", color = Color.White, fontSize = 20.sp)
            }
        }
    }
}

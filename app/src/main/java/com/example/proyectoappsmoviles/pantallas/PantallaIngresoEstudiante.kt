package com.example.proyectoappsmoviles.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.example.proyectoappsmoviles.datos.Evaluacion
import com.example.proyectoappsmoviles.datos.EvaluacionDao
import com.example.proyectoappsmoviles.datos.RespuestaDao
import kotlinx.coroutines.launch

@Composable
fun PantallaIngresoEstudiante(
    respuestaDao: RespuestaDao,
    evaluacionDao: EvaluacionDao,
    onAccesoCorrecto: (Evaluacion) -> Unit,
    onVolver: () -> Unit
) {
    var codigo by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val colorRojo = Color(0xFF832A2D)

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ingresa tu código",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Escribe el código que te entregó el psicólogo.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = codigo,
                onValueChange = { if (it.length <= 7) codigo = it.uppercase() },
                label = { Text("Código") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val pin = codigo.trim()
                    if (pin.isBlank()) {
                        mensaje = "Escribe tu código"
                    } else {
                        scope.launch {
                            val evaluacion = respuestaDao.buscarEvaluacionPorPin(pin)
                            if (evaluacion == null) {
                                mensaje = "Código incorrecto"
                            } else if (evaluacion.estado == "Pendiente" || evaluacion.estado == "En curso") {
                                val actualizada = evaluacion.copy(estado = "En curso")
                                evaluacionDao.actualizarEvaluacion(actualizada)
                                mensaje = "Código correcto"
                                onAccesoCorrecto(actualizada)
                            } else {
                                mensaje = "Este código ya no está disponible"
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = colorRojo)
            ) {
                Text("Ingresar", color = Color.White)
            }

            if (mensaje.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = mensaje, color = colorRojo)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onVolver,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("Volver", color = Color.White)
            }
        }
    }
}
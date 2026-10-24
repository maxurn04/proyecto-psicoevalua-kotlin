package com.example.proyectoappsmoviles.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import com.example.proyectoappsmoviles.datos.Evaluacion
import com.example.proyectoappsmoviles.datos.EvaluacionDao
import kotlinx.coroutines.launch

@Composable
fun DetalleEvaluacion(
    evaluacion: Evaluacion,
    evaluacionDao: EvaluacionDao,
    onGuardado: () -> Unit,
    onVolver: () -> Unit
) {
    var nuevaObservacion by remember { mutableStateOf(evaluacion.observacionPsicologo) }
    var estadoActual by remember { mutableStateOf(evaluacion.estado) }
    var mensaje by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val colorRojo = Color(0xFF832A2D)
    val colorRosa = Color(0xFFFBECEC)
    val colorAmarillo = Color(0xFFFBE8C5)

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onVolver,
                    colors = ButtonDefaults.buttonColors(containerColor = colorRojo)
                ) {
                    Text(" ← ", color = Color.White)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Revisión profesional",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = colorRosa)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Estudiante: ${evaluacion.idEstudianteFicticio} (ficticio)",
                        style = MaterialTheme.typography.titleMedium,
                        color = colorRojo
                    )
                    Text(text = "Curso: ${evaluacion.cursoEstudiante}")
                    Text(text = "Instrumento: ${evaluacion.nombreInstrumento}")
                    Text(text = "Código PIN: ${evaluacion.codigoPin}")
                    Text(text = "Estado actual: $estadoActual")
                    Text(
                        text = "Puntaje de prueba: ${evaluacion.puntajeTotal} pts",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = colorAmarillo)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Criterio profesional exclusivo",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "Este resultado es referencial y no constituye un diagnóstico clínico automático. La interpretación corresponde exclusivamente al psicólogo PIE.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nuevaObservacion,
                onValueChange = { nuevaObservacion = it },
                label = { Text("Observación profesional ficticia") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { estadoActual = "Por revisar" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (estadoActual == "Por revisar") colorRojo else Color.LightGray
                    )
                ) {
                    Text("Por revisar", color = Color.White)
                }
                Button(
                    onClick = { estadoActual = "Completada" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (estadoActual == "Completada") colorRojo else Color.LightGray
                    )
                ) {
                    Text("Completada", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (nuevaObservacion.isNotBlank()) {
                        scope.launch {
                            evaluacionDao.actualizarEvaluacion(
                                Evaluacion(
                                    id = evaluacion.id,
                                    codigoPin = evaluacion.codigoPin,
                                    idEstudianteFicticio = evaluacion.idEstudianteFicticio,
                                    cursoEstudiante = evaluacion.cursoEstudiante,
                                    nombreInstrumento = evaluacion.nombreInstrumento,
                                    estado = estadoActual,
                                    puntajeTotal = evaluacion.puntajeTotal,
                                    observacionPsicologo = nuevaObservacion
                                )
                            )
                            onGuardado()
                        }
                    } else {
                        mensaje = "Ingresa una observación antes de guardar"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = colorRojo)
            ) {
                Text("Guardar observación y estado", color = Color.White)
            }

            if (mensaje.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = mensaje, color = colorRojo)
            }
        }
    }
}
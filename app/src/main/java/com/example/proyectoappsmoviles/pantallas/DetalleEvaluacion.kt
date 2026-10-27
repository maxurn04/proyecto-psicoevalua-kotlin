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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.proyectoappsmoviles.datos.Pregunta
import com.example.proyectoappsmoviles.datos.PreguntaDao
import com.example.proyectoappsmoviles.datos.Respuesta
import com.example.proyectoappsmoviles.datos.RespuestaDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DetalleEvaluacion(
    evaluacion: Evaluacion,
    evaluacionDao: EvaluacionDao,
    preguntaDao: PreguntaDao,
    respuestaDao: RespuestaDao,
    onGuardado: () -> Unit,
    onVolver: () -> Unit
) {
    var idEst by remember { mutableStateOf("MPZ-${evaluacion.id + 100}") }
    var estadoActual by remember { mutableStateOf(evaluacion.estado) }
    var mensaje by remember { mutableStateOf("") }
    var mensObservacion by remember { mutableStateOf(evaluacion.observacionPsicologo) }
    var listaRespuestas by remember { mutableStateOf(listOf<Respuesta>()) }
    var listaPreguntas by remember { mutableStateOf(listOf<Pregunta>()) }
    val opcionesTexto = listOf("Nunca", "A veces", "Casi siempre", "Siempre")
    val scope = rememberCoroutineScope()

    LaunchedEffect(evaluacion.id) {
        scope.launch(Dispatchers.IO) {
            listaPreguntas = preguntaDao.obtenerPorInstrumento(evaluacion.nombreInstrumento)
            listaRespuestas = respuestaDao.obtenerPorEvaluacion(evaluacion.id)
        }
    }

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
                    Text("Volver", color = Color.White, style = MaterialTheme.typography.labelSmall)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Detalle de Formulario",
                    style = MaterialTheme.typography.titleLarge,

                    )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = colorRosa)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Estudiante: ${evaluacion.idEstudianteFicticio}",
                        style = MaterialTheme.typography.titleMedium,
                        color = colorRojo
                    )
                    Text(text = "ID Estudiante: $idEst")
                    Text(text = "Curso: ${evaluacion.cursoEstudiante}", color = Color.DarkGray)
                    Text(text = "Instrumento: ${evaluacion.nombreInstrumento}", color = Color.DarkGray)
                    Text(text = "Codigo de Ingreso: ${evaluacion.codigoPin}", color = Color.DarkGray)
                    Text(text = "Estado actual: $estadoActual", color = Color.DarkGray)
                    Text(
                        text = "Puntaje de prueba: ${evaluacion.puntajeTotal} pts",
                        color = Color.DarkGray
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

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Respuestas del estudiante:",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                if (listaRespuestas.isEmpty()) {
                    item {
                        Text(text = "Sin respuestas registradas aún.", color = Color.Gray)
                    }
                } else {
                    items(listaRespuestas) { resp ->
                        val preg = listaPreguntas.find { it.id == resp.idPregunta }
                        val textoOpcion = opcionesTexto.getOrElse(resp.opcionElegida) { "" }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = preg?.texto ?: "Pregunta ${resp.idPregunta}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "Respuesta: $textoOpcion (${resp.puntos} pts)",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = colorRojo
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = mensObservacion,
                onValueChange = { mensObservacion = it },
                label = { Text("Observacion") },
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
                    Text("Por revisar", color = Color.DarkGray)
                }
                Button(
                    onClick = { estadoActual = "Completada" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (estadoActual == "Completada") colorRojo else Color.LightGray
                    )
                ) {
                    Text("Completada", color = Color.DarkGray)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (mensObservacion.isNotBlank()) {
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
                                    observacionPsicologo = mensObservacion
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

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    scope.launch {
                        evaluacionDao.eliminarEvaluacion(evaluacion)
                        onVolver()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = colorRosa)
            ) {
                Text("Borrar evaluación", color = colorRojo)
            }

            if (mensaje.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = mensaje, color = colorRojo)
            }
        }
    }
}
package com.example.proyectoappsmoviles.pantallas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectoappsmoviles.datos.Evaluacion
import com.example.proyectoappsmoviles.datos.Pregunta
import com.example.proyectoappsmoviles.datos.PreguntaDao
import com.example.proyectoappsmoviles.datos.Respuesta
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton

private val opcionesRespuesta = listOf("Nunca", "A veces", "Casi siempre", "Siempre")

@Composable
fun PantallaResponderPrueba(
    evaluacion: Evaluacion,
    preguntaDao: PreguntaDao,
    onTerminar: (List<Respuesta>) -> Unit
) {
    var preguntas by remember { mutableStateOf<List<Pregunta>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }
    var indiceActual by remember { mutableStateOf(0) }
    val seleccionadas = remember { mutableStateMapOf<Int, Int>() }
    var mostrarConfirmacion by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    val colorRojo = Color(0xFF832A2D)
    val colorRosa = Color(0xFFFBECEC)
    val colorGris = Color(0xFFF3EFF4)

    LaunchedEffect(evaluacion.nombreInstrumento) {
        preguntas = preguntaDao.obtenerPorInstrumento(evaluacion.nombreInstrumento)
        cargando = false
    }

    fun construirRespuestas(): List<Respuesta> {
        return preguntas
            .filter { seleccionadas.containsKey(it.id) }
            .map {
                val opcion = seleccionadas[it.id] ?: 0
                Respuesta(
                    idEvaluacion = evaluacion.id,
                    idPregunta = it.id,
                    opcionElegida = opcion,
                    puntos = opcion
                )
            }
    }

    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacion = false },
            title = { Text("¿Enviar tus respuestas?") },
            text = { Text("Cuando las envíes ya no podrás cambiarlas.") },
            confirmButton = {
                TextButton(onClick = {
                    mostrarConfirmacion = false
                    onTerminar(construirRespuestas())
                }) { Text("Enviar") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmacion = false }) { Text("Revisar") }
            }
        )
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (cargando) {
                Text("Cargando preguntas...", fontSize = 18.sp)
            } else if (preguntas.isEmpty()) {
                Text("Este cuestionario todavía no tiene preguntas.", fontSize = 18.sp)
            } else {
                val pregunta = preguntas[indiceActual]
                val ultima = indiceActual == preguntas.size - 1

                Text(
                    text = "Pregunta ${indiceActual + 1} de ${preguntas.size}",
                    style = MaterialTheme.typography.titleMedium,
                    color = colorRojo
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { (indiceActual + 1).toFloat() / preguntas.size },
                    modifier = Modifier.fillMaxWidth(),
                    color = colorRojo
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = pregunta.texto,
                    fontSize = 22.sp,
                    lineHeight = 30.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                opcionesRespuesta.forEachIndexed { indice, texto ->
                    val marcada = seleccionadas[pregunta.id] == indice
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                seleccionadas[pregunta.id] = indice
                                mensajeError = ""
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (marcada) colorRosa else colorGris
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = marcada,
                                onClick = {
                                    seleccionadas[pregunta.id] = indice
                                    mensajeError = ""
                                }
                            )
                            Text(text = texto, fontSize = 18.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                if (mensajeError.isNotBlank()) {
                    Text(text = mensajeError, color = colorRojo, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { indiceActual -= 1 },
                        enabled = indiceActual > 0,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                    ) {
                        Text("Anterior", fontSize = 18.sp)
                    }
                    Button(
                        onClick = {
                            if (!ultima) {
                                indiceActual += 1
                            } else {
                                val primeraSinResponder = preguntas.indexOfFirst { !seleccionadas.containsKey(it.id) }
                                if (primeraSinResponder >= 0) {
                                    mensajeError = "Te falta responder la pregunta ${primeraSinResponder + 1}"
                                    indiceActual = primeraSinResponder
                                } else {
                                    mensajeError = ""
                                    mostrarConfirmacion = true
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorRojo)
                    ) {
                        Text(
                            text = if (ultima) "Terminar" else "Siguiente",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}
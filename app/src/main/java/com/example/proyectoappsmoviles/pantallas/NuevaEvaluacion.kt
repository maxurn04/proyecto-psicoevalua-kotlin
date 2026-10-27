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

fun generarPinAcceso(): String {
    val abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val digitos = "0123456789"
    val c1 = abecedario.random()
    val c2 = abecedario.random()
    val c3 = abecedario.random()
    val n1 = digitos.random()
    val n2 = digitos.random()
    val n3 = digitos.random()
    return "$c1$c2$c3-$n1$n2$n3"
}

@Composable
fun NuevaEvaluacion(
    evaluacionDao: EvaluacionDao,
    nombreInstrumento: String = "Cuestionario de adaptación escolar",
    onEvaluacionGuardada: (String, String) -> Unit,
    onVolver: () -> Unit
) {
    var EstudianteFicticio by remember { mutableStateOf("") }
    var cursoEstudiante by remember { mutableStateOf("") }
    var idEstudianteDemo by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val colorRojo = Color(0xFF832A2D)

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
                    text = "Nuevo formulario",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "INSTRUMENTO SELECCIONADO",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = nombreInstrumento,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Text(
                        text = "Simulado",
                        style = MaterialTheme.typography.labelSmall,
                        color = colorRojo
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = EstudianteFicticio,
                onValueChange = { EstudianteFicticio = it },
                label = { Text("Nombre Estudiante") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = cursoEstudiante,
                    onValueChange = { cursoEstudiante = it },
                    label = { Text("Curso") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = idEstudianteDemo,
                    onValueChange = { idEstudianteDemo = it },
                    label = { Text("ID Estudiante") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (EstudianteFicticio.isNotBlank() && cursoEstudiante.isNotBlank() && idEstudianteDemo.isNotBlank()) {
                        val pinAsignado = generarPinAcceso()
                        scope.launch {
                            evaluacionDao.insertarEvaluacion(
                                Evaluacion(
                                    codigoPin = pinAsignado,
                                    idEstudianteFicticio = EstudianteFicticio,
                                    cursoEstudiante = cursoEstudiante,
                                    nombreInstrumento = nombreInstrumento,
                                    estado = "Pendiente",
                                    puntajeTotal = 0,
                                    observacionPsicologo = ""
                                )
                            )
                            onEvaluacionGuardada(EstudianteFicticio, pinAsignado)
                        }
                    } else {
                        mensaje = "Completa los campos obligatorios"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = colorRojo)
            ) {
                Text("Revisar y generar código", color = Color.White)
            }

            if (mensaje.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = mensaje)
            }
        }
    }
}
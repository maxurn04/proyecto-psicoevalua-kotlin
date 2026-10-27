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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class ItemInstrumento(
    val nombre: String,
    val tipo: String,
    val finalidad: String,
    val nivel: String,
    val tiempo: String,
    val instrucciones: String,
    val estado: String,
    val autorizada: Boolean
)

@Composable
fun CatalogoInstrumentos(
    onSeleccionarInstrumento: (String) -> Unit,
) {
    val colorRojo = Color(0xFF832A2D)
    val colorRosa = Color(0xFFE5C1C5)
    val colorVerde = Color(0xFFBCE3D8)
    val colorAmarillo = Color(0xFFFBE8C5)

    val listaInstrumentos = listOf(
        ItemInstrumento(
            nombre = "Cuestionario de Adaptación Escolar",
            tipo = "Cuestionario educativo simulado",
            finalidad = "Evaluar integración en aula y bienestar socioemocional del estudiante.",
            nivel = "1° a 8° Básico",
            tiempo = "15 minutos",
            instrucciones = "Aplicación individual supervisada en sala PIE sin interrupciones.",
            estado = "Disponible",
            autorizada = true
        ),
        ItemInstrumento(
            nombre = "Escala de Hábitos de Estudio",
            tipo = "Escala psicoeducativa simulada",
            finalidad = "Identificar rutinas, uso del tiempo escolar y concentración en tareas.",
            nivel = "5° a 8° Básico",
            tiempo = "20 minutos",
            instrucciones = "Lectura guiada de alternativas de frecuencia junto al profesional.",
            estado = "Disponible",
            autorizada = true
        ),
        ItemInstrumento(
            nombre = "Batería de Habilidades Cognitivas",
            tipo = "Prueba psicométrica estandarizada",
            finalidad = "Exploración de memoria de trabajo, velocidad de procesamiento y comprensión verbal.",
            nivel = "3° a 7° Básico",
            tiempo = "45 minutos",
            instrucciones = "Requiere adquisición de licencia institucional y baremos oficiales.",
            estado = "Pendiente de autorización",
            autorizada = false
        )
    )

    Scaffold { Column ->
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Catálogo de Instrumentos",
                    style = MaterialTheme.typography.titleLarge,

                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = colorAmarillo)
            ) {
                Column(modifier = Modifier.padding(7.dp)) {
                    Text(
                        text = "Marco de uso ético PIE",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "Solo se aplican pruebas simuladas o debidamente autorizadas. Las pruebas con derechos protegidos no pueden ser utilizadas sin licencia.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(listaInstrumentos) { inst ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = inst.nombre,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = colorRojo,
                                    modifier = Modifier.weight(1f)
                                )
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (inst.autorizada) colorVerde else colorRosa
                                    )
                                ) {
                                    Text(
                                        text = inst.estado,
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        color = if (inst.autorizada) Color.DarkGray else colorRojo
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = "Tipo: ${inst.tipo}", style = MaterialTheme.typography.bodySmall)
                            Text(text = "Finalidad: ${inst.finalidad}", style = MaterialTheme.typography.bodySmall)
                            Text(text = "Nivel: ${inst.nivel} | Tiempo: ${inst.tiempo}", style = MaterialTheme.typography.bodySmall)
                            Text(text = "Instrucciones: ${inst.instrucciones}", style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.height(10.dp))

                            if (inst.autorizada) {
                                Button(
                                    onClick = { onSeleccionarInstrumento(inst.nombre) },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(containerColor = colorRojo)
                                ) {
                                    Text("Seleccionar y crear aplicación", color = Color.White)
                                }
                            } else {
                                Button(
                                    onClick = { },
                                    enabled = false,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("No disponible para aplicar (sin licencia)")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
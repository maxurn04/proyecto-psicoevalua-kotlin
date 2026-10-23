package com.example.proyectoappsmoviles.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString

@Composable
fun EntregaCodigo(
    codigoPin: String,
    nombreEstudiante: String,
    instrumento: String = "Adaptación escolar",
    cursoEstudiante: String,
    onContinuar: () -> Unit,
    onCancelar: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current //para copiar y pegar el codigo
    val colorRojo = Color(0xFF832A2D)
    val colorRosa = Color(0xFFFBECEC)
    val colorGrisFondo = Color(0xFFF9F9F9)
    var mensajeCopiado by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Button(
                        onClick = onCancelar,
                        colors = ButtonDefaults.buttonColors(containerColor = colorRojo)
                    ) {
                        Text("Volver", color = Color.White,
                            style = MaterialTheme.typography.bodyLarge)

                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Formulario listo!",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Comparte este código solo con la estudiante ficticia y supervisa su ingreso.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = colorRosa)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp, horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "CÓDIGO TEMPORAL",
                            style = MaterialTheme.typography.labelMedium,
                            color = colorRojo
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = codigoPin,
                            style = MaterialTheme.typography.displaySmall,
                            color = colorRojo
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = colorGrisFondo)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Estudiante", color = Color.DarkGray)
                            Text("$nombreEstudiante | $cursoEstudiante")
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Instrumento", color = Color.DarkGray)
                            Text(instrumento)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Acceso", color = Color.DarkGray)
                            Text("Único", color = colorRojo)
                        }
                    }
                }

                if (mensajeCopiado.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = mensajeCopiado, color = colorRojo)
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(codigoPin))
                        mensajeCopiado = "Codigo copiado!"
                        onContinuar()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = colorRojo)
                ) {
                    Text("Copiar código", color = Color.White)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onCancelar,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = colorRosa)
                ) {
                    Text("Cancelar aplicación", color = colorRojo)
                }
            }
        }
    }
}
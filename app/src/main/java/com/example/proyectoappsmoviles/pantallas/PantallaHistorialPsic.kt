package com.example.proyectoappsmoviles.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.proyectoappsmoviles.datos.Evaluacion
import com.example.proyectoappsmoviles.datos.EvaluacionDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PantallaHistorialPsicologo(
    evaluacionDao:EvaluacionDao,
    onRevisarEvaluacion:(Evaluacion)->Unit
){
    var listaEvaluaciones by remember{mutableStateOf(listOf<Evaluacion>())}
    val scope=rememberCoroutineScope()
    val colorRojo=Color(0xFF832A2D)
    val colorRosa=Color(0xFFE5C1C5)

    LaunchedEffect(Unit){
        scope.launch(Dispatchers.IO){
            listaEvaluaciones=evaluacionDao.obtenerEvaluaciones()
        }
    }

    Column(
        modifier=Modifier.fillMaxSize()
    ){
        Text(
            text="Historial y Revisión de Estudiantes",
            style=MaterialTheme.typography.titleMedium
        )
        Spacer(modifier=Modifier.height(8.dp))
        Button(
            onClick={
                scope.launch(Dispatchers.IO){
                    listaEvaluaciones=evaluacionDao.obtenerEvaluaciones()
                }
            },
            modifier=Modifier.fillMaxWidth()
        ){
            Text("Actualizar Historial")
        }
        Spacer(modifier=Modifier.height(8.dp))
        LazyColumn(
            modifier=Modifier.weight(1f)
        ){
            items(listaEvaluaciones){eval->
                Card(
                    modifier=Modifier
                        .fillMaxWidth()
                        .padding(vertical=4.dp)
                ){
                    Column(
                        modifier=Modifier.padding(16.dp)
                    ){
                        Text(
                            text="Estudiante: ${eval.idEstudianteFicticio}",
                            style=MaterialTheme.typography.titleMedium
                        )
                        Text(text=eval.nombreInstrumento)
                        Text(text="Curso: ${eval.cursoEstudiante} - PIN: ${eval.codigoPin}")
                        Text(text="Estado: ${eval.estado} - Puntaje: ${eval.puntajeTotal}")
                        if(eval.observacionPsicologo.isNotEmpty()){
                            Text(text="Observación: ${eval.observacionPsicologo}")
                        }else{
                            Text(text="Observación: Pendiente de revisión")
                        }
                        Spacer(modifier=Modifier.height(8.dp))
                        Row(
                            modifier=Modifier.fillMaxWidth(),
                            horizontalArrangement=Arrangement.spacedBy(8.dp)
                        ){
                            Button(
                                onClick={onRevisarEvaluacion(eval)},
                                modifier=Modifier.weight(1f),
                                colors=ButtonDefaults.buttonColors(containerColor=colorRojo)
                            ){
                                Text("Revisar detalle",color=Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PanelPsicologo(
    evaluacionDao:EvaluacionDao,
    onIrANuevaEvaluacion:()->Unit,
    onRevisarEvaluacion:(Evaluacion)->Unit,
    onCerrarSesion:()->Unit
){
    var listaEvaluaciones by remember{mutableStateOf(listOf<Evaluacion>())}
    var seccionActual by remember{mutableStateOf("inicio")}
    val scope=rememberCoroutineScope()

    val colorRojo=Color(0xFF832A2D)
    val colorVerde=Color(0xFFBCE3D8)
    val colorAmarillo=Color(0xFFFBE8C5)
    val colorRosa=Color(0xFFE5C1C5)

    val activas=listaEvaluaciones.count{it.estado=="Pendiente"||it.estado=="En curso"||it.estado=="Activa"}
    val completadas=listaEvaluaciones.count{it.estado=="Completada"||it.estado=="Finalizada"}
    val porRevisar=listaEvaluaciones.count{it.estado=="Por revisar"||(it.estado=="Completada"&&it.observacionPsicologo.isEmpty())}

    LaunchedEffect(Unit){
        scope.launch(Dispatchers.IO){
            listaEvaluaciones=evaluacionDao.obtenerEvaluaciones()
        }
    }

    Scaffold(
        bottomBar={
            Row(
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(vertical=8.dp,horizontal=12.dp),
                horizontalArrangement=Arrangement.SpaceEvenly,
                verticalAlignment=Alignment.CenterVertically
            ){
                Button(
                    onClick={seccionActual="inicio"},
                    colors=ButtonDefaults.buttonColors(
                        containerColor=if(seccionActual=="inicio") colorRojo else colorRosa
                    )
                ){
                    Text(
                        text="Inicio",
                        color=if(seccionActual=="inicio") Color.White else colorRojo
                    )
                }
                Button(
                    onClick={seccionActual="instrumentos"},
                    colors=ButtonDefaults.buttonColors(
                        containerColor=if(seccionActual=="instrumentos") colorRojo else colorRosa
                    )
                ){
                    Text(
                        text="Instrumentos",
                        color=if(seccionActual=="instrumentos") Color.White else colorRojo
                    )
                }
                Button(
                    onClick={seccionActual="historial"},
                    colors=ButtonDefaults.buttonColors(
                        containerColor=if(seccionActual=="historial") colorRojo else colorRosa
                    )
                ){
                    Text(
                        text="Historial",
                        color=if(seccionActual=="historial") Color.White else colorRojo
                    )
                }
            }
        }
    ){innerPadding->
        Column(
            modifier=Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement=Arrangement.Top,
            horizontalAlignment=Alignment.Start
        ){
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement=Arrangement.SpaceBetween,
                verticalAlignment=Alignment.CenterVertically
            ){
                Text(
                    text="Buenos días, Psicólog@",
                    style=MaterialTheme.typography.titleSmall
                )
                Button(
                    onClick=onCerrarSesion,
                    colors=ButtonDefaults.buttonColors(containerColor=colorRojo)
                ){
                    Text(
                        text="Salir",
                        color=Color.White
                    )
                }
            }
            Spacer(modifier=Modifier.height(16.dp))

            if(seccionActual=="inicio"){
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement=Arrangement.spacedBy(8.dp)
                ){
                    Card(
                        modifier=Modifier.weight(1f),
                        colors=CardDefaults.cardColors(containerColor=colorRojo)
                    ){
                        Column(
                            modifier=Modifier.padding(12.dp)
                        ){
                            Text(
                                text="$activas",
                                style=MaterialTheme.typography.headlineSmall,
                                color=Color.White
                            )
                            Text(
                                text="Activas",
                                color=Color.White
                            )
                        }
                    }
                    Card(
                        modifier=Modifier.weight(1f),
                        colors=CardDefaults.cardColors(containerColor=colorVerde)
                    ){
                        Column(
                            modifier=Modifier.padding(12.dp)
                        ){
                            Text(
                                text="$completadas",
                                style=MaterialTheme.typography.headlineSmall,
                                color=Color.DarkGray
                            )
                            Text(
                                text="Completadas",
                                color=Color.DarkGray
                            )
                        }
                    }
                    Card(
                        modifier=Modifier.weight(1f),
                        colors=CardDefaults.cardColors(containerColor=colorAmarillo)
                    ){
                        Column(
                            modifier=Modifier.padding(12.dp)
                        ){
                            Text(
                                text="$porRevisar",
                                style=MaterialTheme.typography.headlineSmall,
                                color=Color.DarkGray
                            )
                            Text(
                                text="Por revisar",
                                color=Color.DarkGray
                            )
                        }
                    }
                }
                Spacer(modifier=Modifier.height(16.dp))
                Button(
                    onClick=onIrANuevaEvaluacion,
                    modifier=Modifier.fillMaxWidth(),
                    colors=ButtonDefaults.buttonColors(containerColor=colorRosa)
                ){
                    Text(
                        text="+ Nueva aplicación / formulario",
                        color=colorRojo
                    )
                }
                Spacer(modifier=Modifier.height(8.dp))
                Button(
                    onClick={
                        scope.launch(Dispatchers.IO){
                            listaEvaluaciones=evaluacionDao.obtenerEvaluaciones()
                        }
                    },
                    modifier=Modifier.fillMaxWidth()
                ){
                    Text("Cargar / Actualizar Evaluaciones")
                }
                Spacer(modifier=Modifier.height(16.dp))
                Text(
                    text="Aplicaciones recientes",
                    style=MaterialTheme.typography.titleMedium
                )
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
                                    text="${eval.idEstudianteFicticio} (ficticia)",
                                    style=MaterialTheme.typography.titleMedium
                                )
                                Text(text=eval.nombreInstrumento)
                                Text(text="Curso: ${eval.cursoEstudiante} - PIN: ${eval.codigoPin}")
                                Text(text="Estado: ${eval.estado} - Puntaje: ${eval.puntajeTotal}")
                                Spacer(modifier=Modifier.height(8.dp))
                                Button(
                                    onClick={onRevisarEvaluacion(eval)},
                                    colors=ButtonDefaults.buttonColors(containerColor=colorRojo)
                                ){
                                    Text("Revisar detalle",color=Color.White)
                                }
                            }
                        }
                    }
                }
            } else if (seccionActual == "instrumentos") {
        CatalogoInstrumentos(
            onSeleccionarInstrumento = { instrumento ->
                onIrANuevaEvaluacion()
            },
            onVolver = { seccionActual = "inicio" }
        )
    }else if(seccionActual=="historial"){
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
                                Button(
                                    onClick={onRevisarEvaluacion(eval)},
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
}
package com.example.proyectoappsmoviles

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectoappsmoviles.datos.AppDatabase
import com.example.proyectoappsmoviles.datos.EvaluacionDao
import com.example.proyectoappsmoviles.datos.UsuarioDao
import com.example.proyectoappsmoviles.pantallas.EntregaCodigo
import com.example.proyectoappsmoviles.pantallas.NuevaEvaluacion
import com.example.proyectoappsmoviles.pantallas.PanelPsicologo
import com.example.proyectoappsmoviles.pantallas.PantallaInicio
import com.example.proyectoappsmoviles.pantallas.PantallaLoginPsicologo
import com.example.proyectoappsmoviles.pantallas.PantallaRegistroPsicologo
import com.example.proyectoappsmoviles.ui.theme.ProyectoappsmovilesTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.proyectoappsmoviles.datos.Evaluacion
import com.example.proyectoappsmoviles.pantallas.DetalleEvaluacion
import com.example.proyectoappsmoviles.pantallas.CatalogoInstrumentos
import com.example.proyectoappsmoviles.pantallas.PantallaIngresoEstudiante
import com.example.proyectoappsmoviles.pantallas.PantallaInstrucciones
import com.example.proyectoappsmoviles.pantallas.PantallaResponderPrueba
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.activity.compose.BackHandler
import com.example.proyectoappsmoviles.pantallas.PantallaPruebaFinalizada

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.obtenerBBDD(applicationContext)
        val usuarioDao = database.usuarioDao()
        val evaluacionDao = database.evaluacionDao()
        val preferencias = applicationContext.getSharedPreferences("sesion_pie", Context.MODE_PRIVATE)

        setContent {
            ProyectoappsmovilesTheme {
                val navController = rememberNavController()
                AppNavegacion(navController = navController,
                    usuarioDao = usuarioDao, evaluacionDao = evaluacionDao,context = applicationContext)
            }
        }
    }
}

@Composable
fun AppNavegacion(navController: NavHostController,
                  usuarioDao: UsuarioDao,
                  evaluacionDao: EvaluacionDao, context: Context) {
    val preferencias = context.getSharedPreferences("sesion_pie", Context.MODE_PRIVATE)
    var sesionMantenerActiva by remember { mutableStateOf(preferencias.getBoolean("mantener_sesion", false))
    }
    var instrumentoSeleccionado by remember { mutableStateOf("Cuestionario de adaptación escolar") }
    var estudianteCreado by remember { mutableStateOf("") }
    val respuestaDao = AppDatabase.obtenerBBDD(context).respuestaDao()
    val preguntaDao = AppDatabase.obtenerBBDD(context).preguntaDao()
    val scope = rememberCoroutineScope()
    var evaluacionEstudiante by remember { mutableStateOf<Evaluacion?>(null) }
    var pinCreado by remember { mutableStateOf("") }
    var cursoEstudiante by remember { mutableStateOf("") }
    var evaluacionSeleccionada by remember {
        mutableStateOf(
            Evaluacion(
                id = 0,
                codigoPin = "",
                idEstudianteFicticio = "",
                cursoEstudiante = "",
                nombreInstrumento = "",
                estado = "",
                puntajeTotal = 0,
                observacionPsicologo = ""
            )
        )
    }


    NavHost(
        navController = navController,
        startDestination = if (sesionMantenerActiva) "panel_ini_psicologo" else "inicio" //si se guarda sesion, va de una al inicio
    ){
        composable("inicio") {
            PantallaInicio(
                onIrLoginPsicologo = { navController.navigate("login_psicologo") },
                onIrAccesoEstudiantes = { navController.navigate("ingreso_estudiante") }
            )
        }
        composable("ingreso_estudiante") {
            PantallaIngresoEstudiante(
                respuestaDao = respuestaDao,
                evaluacionDao = evaluacionDao,
                onAccesoCorrecto = { eval ->
                    evaluacionEstudiante = eval
                    navController.navigate("instrucciones")
                },

                onVolver = { navController.navigate("inicio") }
            )
        }
        composable("instrucciones") {
            BackHandler(enabled = true) { }
            evaluacionEstudiante?.let { eval ->
                PantallaInstrucciones(
                    evaluacion = eval,
                    onComenzar = { navController.navigate("responder_prueba") }
                )
            }
        }

        composable("responder_prueba") {
            BackHandler(enabled = true) { }
            evaluacionEstudiante?.let { eval ->
                PantallaResponderPrueba(
                    evaluacion = eval,
                    preguntaDao = preguntaDao,
                    onTerminar = { respuestas ->
                        scope.launch {
                            respuestaDao.guardarRespuestas(respuestas)
                            val finalizada = eval.copy(
                                puntajeTotal = respuestas.sumOf { it.puntos },
                                estado = "Finalizada"
                            )
                            evaluacionDao.actualizarEvaluacion(finalizada)
                            evaluacionEstudiante = finalizada
                            navController.navigate("prueba_finalizada")
                        }
                    }
                )
            }
        }

        composable("prueba_finalizada") {
            PantallaPruebaFinalizada(
                onSalir = {
                    evaluacionEstudiante = null
                    navController.navigate("inicio") {
                        popUpTo("inicio") { inclusive = true }
                    }
                }
            )
        }

        composable("login_psicologo") {
            PantallaLoginPsicologo(
                usuarioDao = usuarioDao,
                onLoginExitoso = { recordar ->
                    sesionMantenerActiva = recordar
                    preferencias.edit().putBoolean("mantener_sesion", recordar).apply()
                    navController.navigate("panel_ini_psicologo")
                },
                onIrARegistro = { navController.navigate("registro_psicologo") },
                onVolver = { navController.navigate("inicio") }
            )
        }
        composable("registro_psicologo") {
            PantallaRegistroPsicologo(
                usuarioDao = usuarioDao,
                onRegistroExitoso = { navController.navigate("login_psicologo") },
                onVolver = { navController.navigate("login_psicologo") }
            )
        }
        composable("panel_ini_psicologo"){
            PanelPsicologo(
                evaluacionDao=evaluacionDao,
                onIrANuevaEvaluacion={ instrumento ->
                    instrumentoSeleccionado = instrumento
                    navController.navigate("nueva_evaluacion")
                },
                onRevisarEvaluacion = { eval ->
                    evaluacionSeleccionada = eval
                    navController.navigate("detalle_evaluacion")
                },
                onCerrarSesion = {
                    sesionMantenerActiva = false
                    preferencias.edit().putBoolean("mantener_sesion", false).apply()
                    navController.navigate("inicio")
                }
                //falta las categorias o mostrar todas las eval y revisar por detalle de una evaluacion,,,
            )
        }
        composable("nueva_evaluacion"){
            NuevaEvaluacion(
                evaluacionDao=evaluacionDao,
                nombreInstrumento = instrumentoSeleccionado,
                onEvaluacionGuardada={
                    nombre, pin -> estudianteCreado = nombre; pinCreado = pin; navController.navigate("entrega_codigo")
                },
                onVolver={navController.navigate("panel_ini_psicologo")}
            )
        }
        composable("entrega_codigo") {
            EntregaCodigo(
                codigoPin = pinCreado,
                nombreEstudiante = estudianteCreado,
                cursoEstudiante = cursoEstudiante,
                onContinuar = { navController.navigate("panel_ini_psicologo") },
                onCancelar = { navController.navigate("panel_ini_psicologo") }
            )
        }
        composable("detalle_evaluacion") {
            DetalleEvaluacion(
                evaluacion = evaluacionSeleccionada,
                evaluacionDao = evaluacionDao,
                onGuardado = { navController.navigate("panel_ini_psicologo") },
                onVolver = { navController.navigate("panel_ini_psicologo") }
            )
        }

    }

}



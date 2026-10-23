package com.example.proyectoappsmoviles

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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.obtenerBBDD(applicationContext)
        val usuarioDao = database.usuarioDao()
        val evaluacionDao = database.evaluacionDao()

        setContent {
            ProyectoappsmovilesTheme {
                val navController = rememberNavController()
                AppNavegacion(navController = navController,
                    usuarioDao = usuarioDao, evaluacionDao = evaluacionDao)
            }
        }
    }
}

@Composable
fun AppNavegacion(navController: NavHostController,
                  usuarioDao: UsuarioDao,
                  evaluacionDao: EvaluacionDao) {
    var estudianteCreado by remember { mutableStateOf("") }
    var pinCreado by remember { mutableStateOf("") }
    var cursoEstudiante by remember { mutableStateOf("") }

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ){
        composable("inicio") {
            PantallaInicio(
                onIrLoginPsicologo = { navController.navigate("login_psicologo") },
                onIrAccesoEstudiantes = {  } //falta lado estudiantes (por el codigo)
            )
        }
        composable("login_psicologo") {
            PantallaLoginPsicologo(
                usuarioDao = usuarioDao,
                onLoginExitoso = {navController.navigate("panel_ini_psicologo")},
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
                onIrANuevaEvaluacion={navController.navigate("nueva_evaluacion")},
                onCerrarSesion={navController.navigate("inicio")}
                //falta las categorias o mostrar todas las eval y revisar por detalle de una evaluacion,,,
            )
        }
        composable("nueva_evaluacion"){
            NuevaEvaluacion(
                evaluacionDao=evaluacionDao,
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

    }

}



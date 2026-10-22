package com.example.proyectoappsmoviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectoappsmoviles.datos.AppDatabase
import com.example.proyectoappsmoviles.datos.UsuarioDao
import com.example.proyectoappsmoviles.pantallas.PantallaInicio
import com.example.proyectoappsmoviles.pantallas.PantallaLoginPsicologo
import com.example.proyectoappsmoviles.pantallas.PantallaRegistroPsicologo

import com.example.proyectoappsmoviles.ui.theme.ProyectoappsmovilesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.obtenerBBDD(applicationContext)
        val usuarioDao = database.usuarioDao()

        setContent {
            ProyectoappsmovilesTheme {
                val navController = rememberNavController()
                AppNavegacion(navController = navController,
                    usuarioDao = usuarioDao)
            }
        }
    }
}

@Composable
fun AppNavegacion(navController: NavHostController,
                  usuarioDao: UsuarioDao) {
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
                onLoginExitoso = { }, //falta pasar a pantalla inicio exitoso
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

    }

}



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
import com.example.proyectoappsmoviles.pantallas.PantallaInicio

import com.example.proyectoappsmoviles.ui.theme.ProyectoappsmovilesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.obtenerBBDD(applicationContext)

        setContent {
            ProyectoappsmovilesTheme {
                val navController = rememberNavController()
                AppNavegacion(navController = navController)
            }
        }
    }
}

@Composable
fun AppNavegacion(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "inicio"
    ){
        composable("inicio") {
            PantallaInicio(
                onIrLoginPsicologo = { },
                onIrAccesoEstudiantes = {  }
            )
        }
    }

}



package com.example.proyectoappsmoviles.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import com.example.proyectoappsmoviles.datos.UsuarioDao
import kotlinx.coroutines.launch

@Composable
fun PantallaLoginPsicologo(
    usuarioDao:UsuarioDao,
    onLoginExitoso:(Boolean)->Unit, //para recordar sesion, lo del boleano
    onIrARegistro:()->Unit,
    onVolver:()->Unit
){
    var correo by remember{mutableStateOf("")}
    var contrasena by remember{mutableStateOf("")}
    var mantenerIniciado by remember{mutableStateOf(false)}
    var mensajeEstado by remember{mutableStateOf("")}
    val scope=rememberCoroutineScope()
    val colorRojo=Color(0xFF832A2D)
    val colorRosa=Color(0xFFE5C1C5)

    Scaffold{innerPadding->
        Column(
            modifier=Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement=Arrangement.Top,
            horizontalAlignment=Alignment.CenterHorizontally
        ){
            Button(
                onClick=onVolver,
                modifier=Modifier.align(Alignment.End),
                colors=ButtonDefaults.buttonColors(containerColor=colorRojo)
            ){
                Text(
                    text="Regresar",
                    color=Color.White
                )
            }
            Spacer(modifier=Modifier.height(30.dp))
            Text(
                text="Acceso profesional",
                style=MaterialTheme.typography.headlineMedium,
                color=colorRojo
            )
            Spacer(modifier=Modifier.height(12.dp))
            Card(
                modifier=Modifier.fillMaxWidth()
            ){
                Column(
                    modifier=Modifier.padding(12.dp)
                ){
                    Text(
                        text="Psicólogo/a PIE",
                        style=MaterialTheme.typography.titleMedium,
                        color=colorRojo
                    )
                    Text(text="Acceso restringido y trazable")
                }
            }
            Spacer(modifier=Modifier.height(16.dp))
            OutlinedTextField(
                value=correo,
                onValueChange={correo=it},
                label={Text("Correo ")},
                modifier=Modifier.fillMaxWidth()
            )
            Spacer(modifier=Modifier.height(8.dp))
            OutlinedTextField(
                value=contrasena,
                onValueChange={contrasena=it},
                label={Text("Contraseña")},
                modifier=Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { mantenerIniciado = !mantenerIniciado },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (mantenerIniciado) colorRojo else colorRosa
                )
            ) {
                Text(
                    text = if (mantenerIniciado) "Recordar en este equipo: SÍ" else "Recordar en este equipo: NO",
                    color = if (mantenerIniciado) Color.White else colorRojo
                )
            }
            Spacer(modifier=Modifier.height(16.dp))
            Button(
                onClick={
                    if(correo.isNotBlank()&&contrasena.isNotBlank()){
                        scope.launch{
                            val usuarioEncontrado=usuarioDao.iniciarSesion(correo,contrasena)
                            if(usuarioEncontrado!=null){
                                onLoginExitoso(mantenerIniciado)
                            }else{
                                mensajeEstado="Usuario o contraseña incorrectos"
                            }
                        }
                    }else{
                        mensajeEstado="Completa todos los campos!!!"
                    }
                },
                modifier=Modifier.fillMaxWidth(),
                colors=ButtonDefaults.buttonColors(containerColor=colorRojo)
            ){
                Text(
                    text="Ingresar como profesional",
                    color=Color.White
                )
            }
            Spacer(modifier=Modifier.height(8.dp))
            Button(
                onClick=onIrARegistro,
                modifier=Modifier.fillMaxWidth()
            ){
                Text("Registrarse!")
            }
            Spacer(modifier=Modifier.height(16.dp))
            Card(
                modifier=Modifier.fillMaxWidth()
            ){
                Column(
                    modifier=Modifier.padding(12.dp)
                ){
                    Text(
                        text="Privacidad institucional",
                        style=MaterialTheme.typography.titleSmall,
                        color=colorRojo
                    )
                    Text(text="Solo personal autorizado puede revisar respuestas. Toda acción queda registrada.")
                }
            }
            if(mensajeEstado.isNotBlank()){
                Spacer(modifier=Modifier.height(8.dp))
                Text(text=mensajeEstado)
            }
        }
    }
}
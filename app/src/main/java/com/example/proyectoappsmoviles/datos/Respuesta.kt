package com.example.proyectoappsmoviles.datos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "respuestas_local")
data class Respuesta(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idEvaluacion: Int,
    val idPregunta: Int,
    val opcionElegida: Int,
    val puntos: Int
)
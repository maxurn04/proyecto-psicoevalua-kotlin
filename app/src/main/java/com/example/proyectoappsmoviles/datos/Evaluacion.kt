package com.example.proyectoappsmoviles.datos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "evaluaciones_local")
data class Evaluacion (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val codigoPin: String,
    val idEstudianteFicticio: String,
    val cursoEstudiante: String,
    val nombreInstrumento: String,
    val estado: String,
    val puntajeTotal: Int = 0,
    val observacionPsicologo: String = ""
)
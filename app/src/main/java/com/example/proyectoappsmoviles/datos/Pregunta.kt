package com.example.proyectoappsmoviles.datos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "preguntas_local")
data class Pregunta(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idInstrumento: Int,
    val orden: Int,
    val texto: String
)
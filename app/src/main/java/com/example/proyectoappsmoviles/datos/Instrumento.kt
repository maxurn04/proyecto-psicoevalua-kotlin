package com.example.proyectoappsmoviles.datos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "instrumentos_local")
data class Instrumento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val tiempoMinutos: Int,
    val rangoEdad: String,
    val estado: String = "Disponible"
)
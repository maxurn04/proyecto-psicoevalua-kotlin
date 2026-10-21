package com.example.proyectoappsmoviles.datos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario_local")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreCompleto: String,
    val usuario: String,
    val contrasena: String
)
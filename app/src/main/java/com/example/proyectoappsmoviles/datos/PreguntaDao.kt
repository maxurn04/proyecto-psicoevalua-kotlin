package com.example.proyectoappsmoviles.datos

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PreguntaDao {
    @Query("SELECT * FROM preguntas_local WHERE nombreInstrumento LIKE '%' || :nombre || '%' OR :nombre LIKE '%' || nombreInstrumento || '%' ORDER BY orden")
    suspend fun obtenerPorInstrumento(nombre: String): List<Pregunta>
}
package com.example.proyectoappsmoviles.datos

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PreguntaDao {
    @Query("SELECT * FROM preguntas_local WHERE idInstrumento = :idInstrumento ORDER BY orden")
    suspend fun obtenerPorInstrumento(idInstrumento: Int): List<Pregunta>
}
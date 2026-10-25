package com.example.proyectoappsmoviles.datos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RespuestaDao {
    @Insert
    suspend fun guardarRespuestas(respuestas: List<Respuesta>)

    @Query("SELECT * FROM respuestas_local WHERE idEvaluacion = :idEvaluacion ORDER BY idPregunta")
    suspend fun obtenerPorEvaluacion(idEvaluacion: Int): List<Respuesta>

    @Query("SELECT * FROM evaluaciones_local WHERE codigoPin = :pin LIMIT 1")
    suspend fun buscarEvaluacionPorPin(pin: String): Evaluacion?
}
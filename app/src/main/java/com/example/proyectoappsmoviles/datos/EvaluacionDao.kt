package com.example.proyectoappsmoviles.datos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface EvaluacionDao {

    @Insert
    suspend fun insertarEvaluacion(evaluacion: Evaluacion)

    @Update
    suspend fun actualizarEvaluacion(evaluacion: Evaluacion)

    @Delete
    suspend fun eliminarEvaluacion(evaluacion: Evaluacion)

    @Query("SELECT * FROM evaluaciones_local ORDER BY id DESC")
    fun obtenerEvaluaciones(): List<Evaluacion>

    @Query("SELECT * FROM evaluaciones_local WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Int): Evaluacion?
}
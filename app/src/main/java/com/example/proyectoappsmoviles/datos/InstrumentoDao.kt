package com.example.proyectoappsmoviles.datos

import androidx.room.Dao
import androidx.room.Query

@Dao
interface InstrumentoDao {
    @Query("SELECT * FROM instrumentos_local ORDER BY id")
    suspend fun obtenerInstrumentos(): List<Instrumento>

    @Query("SELECT * FROM instrumentos_local WHERE titulo LIKE '%' || :titulo || '%' OR :titulo LIKE '%' || titulo || '%' ORDER BY id LIMIT 1")
    suspend fun buscarPorTitulo(titulo: String): Instrumento?
}
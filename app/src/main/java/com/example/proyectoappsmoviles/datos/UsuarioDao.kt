package com.example.proyectoappsmoviles.datos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {
    @Insert
    suspend fun registrarUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuario_local WHERE usuario = :user AND contrasena = :pass")
    suspend fun iniciarSesion(user: String, pass: String): Usuario?

    @Query("SELECT * FROM usuario_local WHERE usuario = :user")
    suspend fun buscarPorUsuario(user: String): Usuario?
}
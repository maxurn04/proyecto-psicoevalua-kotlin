package com.example.proyectoappsmoviles.datos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {
    @Insert
    suspend fun registrarUsuario(correo: Usuario)

    @Query("SELECT * FROM usuario_local WHERE correo = :correo AND contrasena = :pass")
    suspend fun iniciarSesion(correo: String, pass: String): Usuario?

    @Query("SELECT * FROM usuario_local WHERE correo = :correo")
    suspend fun buscarPorUsuario(correo: String): Usuario?
}
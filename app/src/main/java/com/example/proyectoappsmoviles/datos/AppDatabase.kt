package com.example.proyectoappsmoviles.datos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(
    entities = [
        Usuario::class,
        Evaluacion::class
    ],
    version = 2
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun evaluacionDao(): EvaluacionDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun obtenerBBDD(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mpaz_psicoevalua_bbdd"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
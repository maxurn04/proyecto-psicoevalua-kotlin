package com.example.proyectoappsmoviles.datos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(
    entities = [
        Usuario::class,
        Evaluacion::class,
        Pregunta::class,
        Respuesta::class
    ],
    version = 6
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun evaluacionDao(): EvaluacionDao
    abstract fun preguntaDao(): PreguntaDao
    abstract fun respuestaDao(): RespuestaDao

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
                    .fallbackToDestructiveMigration()
                    .addCallback(SemillaDatos.callback)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


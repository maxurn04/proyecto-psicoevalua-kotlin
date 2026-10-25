package com.example.proyectoappsmoviles.datos

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

private class SemillaCuestionario(
    val nombre: String,
    val preguntas: List<String>
)

object SemillaDatos {

    private val cuestionarios = listOf(
        SemillaCuestionario(
            "Cuestionario de Adaptación Escolar",
            listOf(
                "Me siento tranquilo/a cuando llego a la escuela.",
                "Me resulta fácil hacer amigos en mi curso.",
                "Entiendo lo que explican los profesores en clase.",
                "Me animo a pedir ayuda cuando no entiendo algo.",
                "Me siento parte de mi curso."
            )
        ),
        SemillaCuestionario(
            "Cuestionario Socioemocional",
            listOf(
                "Puedo contar cómo me siento a otras personas.",
                "Me llevo bien con mis compañeros.",
                "Cuando algo me molesta, logro calmarme.",
                "Me siento escuchado/a cuando hablo.",
                "Termino lo que empiezo aunque sea difícil."
            )
        ),
        SemillaCuestionario(
            "Escala de Hábitos de Estudio",
            listOf(
                "Reviso mis cuadernos después de clases.",
                "Tengo un lugar tranquilo para estudiar.",
                "Organizo mis tareas antes de empezar.",
                "Estudio con anticipación antes de una prueba.",
                "Pido ayuda cuando algo no me queda claro."
            )
        )
    )

    val callback = object : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            val vacia = db.query("SELECT COUNT(*) FROM preguntas_local").use { cursor ->
                cursor.moveToFirst() && cursor.getInt(0) == 0
            }
            if (!vacia) return

            db.beginTransaction()
            try {
                cuestionarios.forEach { cuestionario ->
                    cuestionario.preguntas.forEachIndexed { pos, texto ->
                        db.execSQL(
                            "INSERT INTO preguntas_local (nombreInstrumento, orden, texto) VALUES (?, ?, ?)",
                            arrayOf<Any?>(cuestionario.nombre, pos + 1, texto)
                        )
                    }
                }
                db.setTransactionSuccessful()
            } finally {
                db.endTransaction()
            }
        }
    }
}
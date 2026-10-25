package com.example.proyectoappsmoviles.datos

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

private class SemillaInstrumento(
    val titulo: String,
    val descripcion: String,
    val minutos: Int,
    val rango: String,
    val preguntas: List<String>
)

object SemillaDatos {

    private val instrumentos = listOf(
        SemillaInstrumento(
            "Cuestionario de Adaptación Escolar",
            "Cuestionario educativo simulado sobre cómo se siente el estudiante en su escuela.",
            15, "1° a 8° Básico",
            listOf(
                "Me siento tranquilo/a cuando llego a la escuela.",
                "Me resulta fácil hacer amigos en mi curso.",
                "Entiendo lo que explican los profesores en clase.",
                "Me animo a pedir ayuda cuando no entiendo algo.",
                "Me siento parte de mi curso."
            )
        ),
        SemillaInstrumento(
            "Escala de Hábitos de Estudio",
            "Escala educativa simulada sobre organización y hábitos de estudio.",
            20, "5° a 8° Básico",
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
            val vacia = db.query("SELECT COUNT(*) FROM instrumentos_local").use { cursor ->
                cursor.moveToFirst() && cursor.getInt(0) == 0
            }
            if (!vacia) return

            db.beginTransaction()
            try {
                instrumentos.forEachIndexed { indice, inst ->
                    val idInstrumento = indice + 1
                    db.execSQL(
                        "INSERT INTO instrumentos_local (id, titulo, descripcion, tiempoMinutos, rangoEdad, estado) VALUES (?, ?, ?, ?, ?, ?)",
                        arrayOf<Any?>(idInstrumento, inst.titulo, inst.descripcion, inst.minutos, inst.rango, "Disponible")
                    )
                    inst.preguntas.forEachIndexed { pos, texto ->
                        db.execSQL(
                            "INSERT INTO preguntas_local (idInstrumento, orden, texto) VALUES (?, ?, ?)",
                            arrayOf<Any?>(idInstrumento, pos + 1, texto)
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
package com.example.sistema_prestamos.Contrato
import com.example.sistema_prestamos.Modelo.Equipo

interface ContratoSisPres {

    interface Vista {
        fun mostrarDatos(equipos: List<Equipo>)
        fun mostrarError(mensaje: String)
    }

    interface Presentador {
        fun obtenerEquipos()
    }

    interface Modelo {
        // Interfaz para comunicar la respuesta asíncrona al Presentador
        interface OnFinishedListener {
            fun onResult(equipos: List<Equipo>)
            fun onError(mensaje: String)
        }

        // El modelo recibe un listener para avisar cuando termine de descargar
        fun descargarEquipos(listener: OnFinishedListener)
    }
}

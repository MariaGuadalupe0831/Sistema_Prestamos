package com.example.sistema_prestamos.Presentador

import com.example.sistema_prestamos.Contrato.ContratoSisPres
import com.example.sistema_prestamos.Modelo.Equipo
import com.example.sistema_prestamos.Modelo.EquipoModelo

class CatalogoVPres(private val vista: ContratoSisPres.Vista) :
    ContratoSisPres.Presentador,
    ContratoSisPres.Modelo.OnFinishedListener { // El presentador escucha al modelo

    private val modelo: ContratoSisPres.Modelo = EquipoModelo()

    // 1. La Vista llama a esto
    override fun obtenerEquipos() {
        modelo.descargarEquipos(this)
    }

    // 2. El Modelo responde con éxito
    override fun onResult(equipos: List<Equipo>) {
        vista.mostrarDatos(equipos)
    }

    // 3. El Modelo responde con error
    override fun onError(mensaje: String) {
        vista.mostrarError(mensaje)
    }
}

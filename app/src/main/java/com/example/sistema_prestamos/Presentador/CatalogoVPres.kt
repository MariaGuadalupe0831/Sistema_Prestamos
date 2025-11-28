package com.example.sistema_prestamos.Presentador
import com.example.sistema_prestamos.Contrato.ContratoSisPres
import com.example.sistema_prestamos.Modelo.EqYAcModelo
class CatalogoVPres(val vista: ContratoSisPres.Vista): ContratoSisPres.Presentador {
    val modelo= EqYAcModelo()
    override fun loadData() {
        val lista=modelo.loadEqYAcs();
        vista.loadData(lista)
    }
}
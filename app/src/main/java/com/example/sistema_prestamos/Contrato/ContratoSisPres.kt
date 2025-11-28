package com.example.sistema_prestamos.Contrato
import com.example.sistema_prestamos.Modelo.EqYAc
interface ContratoSisPres {
        interface Vista{
            fun loadData(Data: List<EqYAc>)
        }
        interface Presentador{
            fun loadData()
        }
        interface Modelo{
            fun loadEqYAcs(): List<EqYAc>
        }
}
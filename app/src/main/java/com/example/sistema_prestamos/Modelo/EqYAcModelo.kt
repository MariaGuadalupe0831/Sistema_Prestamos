package com.example.sistema_prestamos.Modelo
import com.example.sistema_prestamos.Contrato.ContratoSisPres
class EqYAcModelo : ContratoSisPres.Modelo {
    lateinit var lista: List<EqYAc>
    override fun loadEqYAcs(): List<EqYAc> {
        lista = listOf(
            EqYAc(1, 1, "H19G", "Cafe H19 en Grano", 18),
            EqYAc(1, 2, "H19M", "Cafe H19 en Molido", 18),
            EqYAc(1, 3, "H16G", "Cafe H16 en Grano", 2),
            EqYAc(1, 4, "H16M", "Cafe H16 en Molido", 2),
            EqYAc(1, 5, "H29G", "Cafe H29 en Grano", 2),
            EqYAc(1, 6, "H29M", "Cafe H29 en Molido", 2),
            EqYAc(1, 7, "H24M", "Cafe H24 en Molido", 2),
            EqYAc(1, 8, "H24G", "Cafe H24 en Grano", 2)
        )
        return lista
    }
}
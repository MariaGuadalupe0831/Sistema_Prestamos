package com.example.sistema_prestamos.Vista

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistema_prestamos.Contrato.ContratoSisPres
import com.example.sistema_prestamos.Modelo.Equipo
import com.example.sistema_prestamos.Presentador.CatalogoVPres
import com.example.sistema_prestamos.R

class CatalogoViewpoint : AppCompatActivity(), ContratoSisPres.Vista {

    private lateinit var rcvCat: RecyclerView
    private lateinit var adapter: CatalogoAdapter
    private lateinit var presentador: CatalogoVPres
    private lateinit var btnRegresar: Button // <--- 1. Declaramos el botón

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogoviewpoint)

        // Configurar RecyclerView
        rcvCat = findViewById(R.id.rcvEquipos)
        rcvCat.layoutManager = LinearLayoutManager(this)
        adapter = CatalogoAdapter(emptyList())
        rcvCat.adapter = adapter

        // --- 2. Lógica del Botón Regresar ---
        btnRegresar = findViewById(R.id.btn_regresar) // Asegúrate de que este ID coincida con tu XML
        btnRegresar.setOnClickListener {
            finish() // Esto cierra el catálogo y te devuelve al Dashboard automáticamente
        }
        // -------------------------------------

        // Inicializar Presentador y pedir datos
        presentador = CatalogoVPres(this)
        presentador.obtenerEquipos()
    }

    override fun mostrarDatos(equipos: List<Equipo>) {
        adapter.actualizarLista(equipos)
    }

    override fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
package com.example.sistema_prestamos.Vista

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistema_prestamos.Contrato.ContratoSisPres
import com.example.sistema_prestamos.Modelo.EqYAc
import com.example.sistema_prestamos.Modelo.EqYAcModelo
import com.example.sistema_prestamos.Presentador.CatalogoVPres
import com.example.sistema_prestamos.R

class CatalogoViewpoint : AppCompatActivity(), ContratoSisPres.Vista {
    lateinit var rcvCat: RecyclerView
    lateinit var presentador: CatalogoVPres

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catalogoviewpoint)

        rcvCat = findViewById(R.id.rcvCafe)
        rcvCat.layoutManager= LinearLayoutManager(this)
        presentador= CatalogoVPres(this)
        presentador.loadData()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun loadData(Data: List<EqYAc>) {
        rcvCat.adapter = CatalogoAdapter(Data)
    }
}
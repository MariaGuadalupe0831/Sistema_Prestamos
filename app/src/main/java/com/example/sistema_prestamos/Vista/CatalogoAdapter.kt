package com.example.sistema_prestamos.Vista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sistema_prestamos.Modelo.EqYAc
import com.example.sistema_prestamos.R

class CatalogoAdapter(private val Catalogos: List<EqYAc>) : RecyclerView.Adapter<com.example.sistema_prestamos.Vista.CatalogoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_catalogo, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cafe = Catalogos[position]
        holder.txvNombre.text = cafe.nombre
    }

    override fun getItemCount(): Int {
        return Catalogos.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txvNombre = ItemView.findViewById<TextView>(R.id.txvNombre)
        val imgfoto = ItemView.findViewById<ImageView>(R.id.imgfoto)
    }
}
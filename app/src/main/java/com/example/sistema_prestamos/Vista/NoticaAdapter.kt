package com.example.sistema_prestamos.Vista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sistema_prestamos.R
import com.example.sistema_prestamos.Modelo.Noticia

class NoticaAdapter(private val noticiasList: List<Noticia>) :
    RecyclerView.Adapter<NoticaAdapter.NoticiaViewHolder>() {

    // 1. Define las referencias a las vistas de una fila (el item_noticia.xml)
    class NoticiaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.tv_noticia_titulo)
        val descripcion: TextView = view.findViewById(R.id.tv_noticia_descripcion)
        val fecha: TextView = view.findViewById(R.id.tv_noticia_fecha)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoticaAdapter.NoticiaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_noticia, parent, false)
        return NoticiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticaAdapter.NoticiaViewHolder, position: Int) {
        val noticia = noticiasList[position]

        holder.titulo.text = noticia.titulo
        // Limitamos la descripción para que no sea muy larga en la vista previa
        holder.descripcion.text = if (noticia.descripcion.length > 100) {
            noticia.descripcion.substring(0, 100) + "..."
        } else {
            noticia.descripcion
        }
        holder.fecha.text = "Publicado: ${noticia.fecha_publicacion.substring(0, 10)}"
    }

    override fun getItemCount(): Int {
        return noticiasList.size
    }
}
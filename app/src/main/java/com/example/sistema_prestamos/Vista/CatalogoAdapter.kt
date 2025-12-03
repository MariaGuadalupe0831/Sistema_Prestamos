package com.example.sistema_prestamos.Vista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sistema_prestamos.Modelo.Equipo
import com.example.sistema_prestamos.R

class CatalogoAdapter(private var listaEquipos: List<Equipo>) : RecyclerView.Adapter<CatalogoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Asegúrate de que estos IDs sean EXACTAMENTE los mismos que en tu item_catalogo.xml
        val nombre: TextView = itemView.findViewById(R.id.tvNombreEquipo)
        val descripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val imagen: ImageView = itemView.findViewById(R.id.imgFoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_catalogo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val equipo = listaEquipos[position]

        holder.nombre.text = equipo.nombre
        holder.descripcion.text = equipo.descripcion

        // --- AGREGA ESTO TEMPORALMENTE ---
        // Esto hará que aparezca un mensajito en la pantalla con la URL que intenta cargar.
        // Si sale vacío, es que la base de datos está vacía.
        android.util.Log.d("URL_IMAGEN", "Link: ${equipo.imagenUrl}")
        // ---------------------------------

        Glide.with(holder.itemView.context)
            .load(equipo.imagenUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.imagen)
    }

    override fun getItemCount(): Int = listaEquipos.size

    fun actualizarLista(nuevaLista: List<Equipo>) {
        listaEquipos = nuevaLista
        notifyDataSetChanged()
    }
}
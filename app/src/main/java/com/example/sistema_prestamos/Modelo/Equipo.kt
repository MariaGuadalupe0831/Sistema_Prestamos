package com.example.sistema_prestamos.Modelo
import com.google.gson.annotations.SerializedName

data class Equipo(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val cantidad: Int,

    // Este DEBE quedarse porque en PHP es "imagen_url" y aquí "imagenUrl"
    @SerializedName("imagen_url") val imagenUrl: String
)

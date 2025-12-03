package com.example.sistema_prestamos.Modelo

data class Noticia(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val fecha_publicacion: String,
    val id_admin: Int
    // No incluimos la imagen blob aquí
)

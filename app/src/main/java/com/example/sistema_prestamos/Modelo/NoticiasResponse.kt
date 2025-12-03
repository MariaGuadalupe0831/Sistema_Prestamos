package com.example.sistema_prestamos.Modelo

data class NoticiasResponse(
    val success: Boolean,
    val message: String,
    val noticias: List<Noticia>
)

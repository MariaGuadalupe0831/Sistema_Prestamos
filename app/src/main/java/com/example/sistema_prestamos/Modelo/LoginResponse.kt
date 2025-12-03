package com.example.sistema_prestamos.Modelo


data class LoginResponse(
    val success: Boolean, // true si el login fue exitoso
    val message: String,  // Mensaje de éxito o error
    val rol: String?
)
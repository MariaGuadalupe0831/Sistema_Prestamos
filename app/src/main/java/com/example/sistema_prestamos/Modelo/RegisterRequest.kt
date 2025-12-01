package com.example.sistema_prestamos.Modelo

data class RegisterRequest(
    val nombre: String,
    val apellido_p: String,
    val apellido_m: String,
    val matricula: String,
    val correo: String,
    val tipo_usuario: String, // Ejemplo: "ALUMNO", "DOCENTE", etc.
    val contrasena: String
)
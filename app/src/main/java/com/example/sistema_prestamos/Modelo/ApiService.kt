package com.example.sistema_prestamos.Modelo

import com.example.sistema_prestamos.Modelo.LoginRequest
import com.example.sistema_prestamos.Modelo.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    // Método para enviar el POST request de login
    @POST("login.php")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    @POST("registro.php")
    suspend fun registerUser(@Body request: RegisterRequest): Response<LoginResponse>
// Nota: Usamos LoginResponse para la respuesta, ya que solo necesitamos success/message
}
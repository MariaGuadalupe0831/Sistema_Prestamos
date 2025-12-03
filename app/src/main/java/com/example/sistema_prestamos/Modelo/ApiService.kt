package com.example.sistema_prestamos.Modelo

// Interfaz que define los métodos para interactuar con tu API REST
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    // Método para enviar el POST request de login
    @POST("login.php")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    @POST("registro.php")
    // Se asume que RegisterRequest y LoginResponse están en el mismo paquete
    suspend fun registerUser(@Body request: RegisterRequest): Response<LoginResponse>

    @POST("get_historial_prestamos.php")
    // Se asume que HistorialRequest y HistorialResponse están en el mismo paquete
    suspend fun getHistorialPrestamos(@Body request: HistorialRequest): Response<HistorialResponse>
}
package com.example.sistema_prestamos.Modelo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://prestamosequipos.grupoahost.com/api/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Expone la interfaz del servicio
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
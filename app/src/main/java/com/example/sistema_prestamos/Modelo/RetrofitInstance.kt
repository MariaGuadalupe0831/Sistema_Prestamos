package com.example.sistema_prestamos.Modelo

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://prestamosequipos.grupoahost.com/api/"

    // Configuración del Interceptor (El "espía" que muestra los datos en consola)
    private val client by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY) // Muestra todo el JSON

        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // Importante: Agregamos el cliente HTTP
            .addConverterFactory(GsonConverterFactory.create(gson)) // Usamos el gson lenient
            .build()
    }
    // Expone la interfaz del servicio
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
package com.example.sistema_prestamos.Modelo

import com.example.sistema_prestamos.Contrato.ContratoSisPres
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EquipoModelo : ContratoSisPres.Modelo {

    override fun descargarEquipos(listener: ContratoSisPres.Modelo.OnFinishedListener) {
        // Llamada asíncrona a Retrofit
        RetrofitInstance.api.obtenerEquipos().enqueue(object : Callback<List<Equipo>> {
            override fun onResponse(call: Call<List<Equipo>>, response: Response<List<Equipo>>) {
                if (response.isSuccessful && response.body() != null) {
                    // ¡Éxito! Avisamos al Presentador
                    listener.onResult(response.body()!!)
                } else {
                    // Error del servidor (ej. 404, 500)
                    listener.onError("Error del servidor: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Equipo>>, t: Throwable) {
                // Error de conexión (sin internet, timeout)
                listener.onError("Fallo de red: ${t.message}")
            }
        })
    }
}

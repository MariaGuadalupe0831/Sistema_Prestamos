package com.example.sistema_prestamos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class login : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnAcceder: Button
    private lateinit var tvRegistrar: TextView
    private lateinit var apiService: ifaceApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etEmail = findViewById(R.id.edtUsuario)
        etPassword = findViewById(R.id.etPassword)
        btnAcceder = findViewById(R.id.btnAcceder)
        tvRegistrar = findViewById(R.id.tvRegistrar)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://prestamosequipos.grupoahost.com/api/") // Cambia esto por tu URL base
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ifaceApiService::class.java)

// Listener para el botón de login
        btnAcceder.setOnClickListener {
            val Correo = etEmail.text.toString()
            val contrasena = etPassword.text.toString()
            acceder(Correo, contrasena)

        }
        // Listener para el botón de login
        btnAcceder.setOnClickListener {
            val Correo = etEmail.text.toString()
            val contrasena = etPassword.text.toString()
            acceder(Correo, contrasena)

        }

// Listener para el texto de registro
        tvRegistrar.setOnClickListener {
            // Redirige al registro
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
    }
    private fun acceder(email: String, password: String) {

        apiService.iniciarSesion(action="login", correo = email, contrasena = password)
            .enqueue(object : Callback<List<clsDatosRespuesta>> {
                override fun onResponse(call: Call<List<clsDatosRespuesta>>,response: Response<List<clsDatosRespuesta>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { datos ->

                            if (datos[0].Estado=="Correcto") {

                                val intent = Intent(this@login, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@login,"No se encontraron datos de películas.",Toast.LENGTH_SHORT).show()
                            }
                        } ?: run {
                            Toast.makeText(this@login,"Respuesta vacía o en formato incorrecto",Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(this@login,"Error en la respuesta del servidor: $errorBody",Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<List<clsDatosRespuesta>>, t: Throwable) {
                    Toast.makeText(this@login,"Error en la conexión: ${t.message}",Toast.LENGTH_SHORT).show()
                }
            })

    }
}
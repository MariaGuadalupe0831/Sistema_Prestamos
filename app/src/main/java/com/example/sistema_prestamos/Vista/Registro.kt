package com.example.sistema_prestamos.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sistema_prestamos.R
import androidx.lifecycle.lifecycleScope
import com.example.sistema_prestamos.Modelo.RetrofitInstance
import com.example.sistema_prestamos.Modelo.RegisterRequest
import kotlinx.coroutines.launch

class Registro : AppCompatActivity() {
    // Declaración de vistas
    private lateinit var etNombre: EditText
    private lateinit var etApellidoP: EditText
    private lateinit var etApellidoM: EditText
    private lateinit var etMatricula: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etTipoUsuario: EditText // Para roles como "ALUMNO", "DOCENTE", etc.
    private lateinit var etPassword: EditText
    private lateinit var btnRegistrar: Button

    // NOTA: Es buena práctica tener un botón para volver al Login, aquí asumimos un botón de atrás estándar.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialización de Vistas (Asegúrate que los IDs coincidan con tu XML)
        etNombre = findViewById(R.id.et_nombre)
        etApellidoP = findViewById(R.id.et_apellido_paterno)
        etApellidoM = findViewById(R.id.et_apellido_materno)
        etMatricula = findViewById(R.id.et_matricula)
        etCorreo = findViewById(R.id.et_correo_registro)
        etTipoUsuario = findViewById(R.id.et_tipo_usuario)
        etPassword = findViewById(R.id.et_password_registro)
        btnRegistrar = findViewById(R.id.btn_registrar)

        // Listener del botón de registro
        btnRegistrar.setOnClickListener {
            performRegistration()
        }

    }
    private fun performRegistration() {
        // 1. Obtener y validar datos de entrada de todos los campos
        val nombre = etNombre.text.toString().trim()
        val apellidoP = etApellidoP.text.toString().trim()
        val apellidoM = etApellidoM.text.toString().trim() // Opcional, pero se envía
        val matricula = etMatricula.text.toString().trim()
        val correo = etCorreo.text.toString().trim()
        val tipoUsuario = etTipoUsuario.text.toString().trim().uppercase() // EJ: ALUMNO
        val password = etPassword.text.toString().trim()

        // Validación: No debe haber campos vacíos (ajusta esta lista si el Apellido Materno es opcional)
        if (nombre.isEmpty() || apellidoP.isEmpty() || correo.isEmpty() || matricula.isEmpty() || tipoUsuario.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos obligatorios.", Toast.LENGTH_LONG).show()
            return
        }

        // 2. Crear el objeto de la solicitud RegisterRequest
        val registerRequest = RegisterRequest(
            nombre = nombre,
            apellido_p = apellidoP,
            apellido_m = apellidoM,
            matricula = matricula,
            correo = correo,
            tipo_usuario = tipoUsuario,
            contrasena = password // Se envía en texto plano, se cifrará en el servidor
        )

        // 3. Ejecutar la llamada a la API en una Corrutina
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.registerUser(registerRequest)

                if (response.isSuccessful) {
                    val registerResult = response.body()
                    if (registerResult != null && registerResult.success) {
                        // Éxito: Informar y volver al Login
                        Toast.makeText(this@Registro, "Registro exitoso. ¡Ya puedes iniciar sesión!", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        // Error reportado por el servidor (ej. "Correo ya registrado")
                        Toast.makeText(this@Registro, registerResult?.message ?: "Error desconocido en el registro.", Toast.LENGTH_LONG).show()
                    }
                } else {
                    // Error de servidor HTTP
                    Toast.makeText(this@Registro, "Error de servidor HTTP: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                // Error de red/conexión
                Toast.makeText(this@Registro, "Error de red: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

}
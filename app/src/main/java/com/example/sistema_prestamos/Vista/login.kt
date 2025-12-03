package com.example.sistema_prestamos.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.launch
import com.example.sistema_prestamos.R
import com.example.sistema_prestamos.Modelo.RetrofitInstance
import com.example.sistema_prestamos.Modelo.LoginRequest
import com.example.sistema_prestamos.Modelo.LoginResponse
import com.example.sistema_prestamos.Modelo.SessionManager


class login : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    // Declaración de vistas (asume que los IDs son et_correo, et_password, btn_login)
    private lateinit var etCorreo: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegistrar: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sessionManager = SessionManager(applicationContext)

        // --- 1. VERIFICACIÓN DE SESIÓN AL INICIO ---
        if (sessionManager.isLoggedIn()) {
            redirectUserByRole(sessionManager.getUserRole())
            return
        }
        // -------------------------------------------

        setContentView(R.layout.activity_login)

        // Inicialización de vistas
        etCorreo = findViewById(R.id.etCorreo)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        // Inicialización del TextView de Registro
        tvRegistrar = findViewById(R.id.tvRegistrar)

        btnLogin.setOnClickListener {
            performLogin()
        }

        tvRegistrar.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
    }
    private fun performLogin() {
        val correo = etCorreo.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Debe ingresar correo y contraseña.", Toast.LENGTH_SHORT).show()
            return
        }

        // Ejecuta la llamada de red
        lifecycleScope.launch {
            try {
                val request = LoginRequest(correo, password)
                val response = RetrofitInstance.api.loginUser(request)

                if (response.isSuccessful) {
                    val loginResult = response.body()
                    if (loginResult != null && loginResult.success) {
                        handleSuccessfulLogin(loginResult)
                    } else {
                        Toast.makeText(this@login, loginResult?.message ?: "Credenciales incorrectas.", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@login, "Error de servidor HTTP: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@login, "Error de conexión: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun handleSuccessfulLogin(loginResult: LoginResponse) {
        val rol = loginResult.rol

        if (rol != null) {
            // Guarda la sesión si el login fue exitoso
            sessionManager.createLoginSession(rol)
            redirectUserByRole(rol)
        } else {
            Toast.makeText(this, "Login exitoso, pero rol no definido.", Toast.LENGTH_LONG).show()
        }
    }

    private fun redirectUserByRole(rol: String?) {
        val targetIntent: Intent = when (rol?.lowercase()) {
            "administrador" -> Intent(this, AdminDashboardActivity::class.java)
            "alumno", "docente", "usuario" -> Intent(this, UserDashboardActivity::class.java)
            else -> {
                // Si el rol es desconocido, por seguridad, cerramos sesión y volvemos al Login
                sessionManager.logout()
                Intent(this, login::class.java)
            }
        }
        startActivity(targetIntent)
        finish()
    }
}
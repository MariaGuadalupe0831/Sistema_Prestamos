package com.example.sistema_prestamos.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sistema_prestamos.R
import com.example.sistema_prestamos.Modelo.SessionManager

class AdminDashboardActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var btnLogout: Button
    private lateinit var tvWelcome: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sessionManager = SessionManager(applicationContext)

        // --- VERIFICACIÓN DE ROL CRÍTICA ---
        // Si el rol guardado no es "administrador", forzamos el cierre de sesión y redirigimos.
        if (sessionManager.getUserRole()?.lowercase() != "administrador") {
            sessionManager.logout()
            startActivity(Intent(this, login::class.java))
            finish()
            return
        }
        // ------------------------------------

        // Inicialización de Vistas
        tvWelcome = findViewById(R.id.tv_admin_welcome)
        btnLogout = findViewById(R.id.btn_admin_logout)

        tvWelcome.text = "Bienvenido, Administrador"

        // Lógica de administrador...

        // Botón de Logout
        btnLogout.setOnClickListener {
            logoutUser()
        }
    }
    private fun logoutUser() {
        sessionManager.logout()
        // Navegamos al Login y cerramos la actividad actual
        startActivity(Intent(this, login::class.java))
        finish()
    }
}
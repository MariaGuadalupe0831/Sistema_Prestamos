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
    private lateinit var btnNoticias: Button
    private lateinit var btnLogout: Button
    private lateinit var btnCatalogo: Button // <--- 1. Declaramos el nuevo botón
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
        if (sessionManager.getUserRole()?.lowercase() != "administrador") {
            sessionManager.logout()
            startActivity(Intent(this, login::class.java)) // Asegúrate de que 'login' empiece con minúscula si así se llama tu clase
            finish()
            return
        }

        // Inicialización de Vistas
        tvWelcome = findViewById(R.id.tv_admin_welcome)
        btnLogout = findViewById(R.id.btn_admin_logout)
        btnCatalogo = findViewById(R.id.btn_ver_catalogo) // <--- 2. Enlazamos con el ID del XML

        tvWelcome.text = "Bienvenido, Administrador"

        // --- LÓGICA DE BOTONES ---

        // Botón para ir al Catálogo
        btnCatalogo.setOnClickListener {
            val intent = Intent(this, CatalogoViewpoint::class.java)
            startActivity(intent)
        }

        // Botón de Logout
        btnLogout.setOnClickListener {
            logoutUser()
        }
    }

    private fun logoutUser() {
        sessionManager.logout()
        // Navegamos al Login y cerramos la actividad actual
        startActivity(Intent(this, login::class.java)) // Asegúrate de que coincida con el nombre de tu clase Login
        finish()
    }
}
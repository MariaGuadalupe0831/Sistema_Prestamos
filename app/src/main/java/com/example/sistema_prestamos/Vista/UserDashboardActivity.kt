package com.example.sistema_prestamos.Vista

import android.content.Intent
import android.widget.Button
import android.widget.TextView
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sistema_prestamos.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.sistema_prestamos.Modelo.RetrofitInstance
import com.example.sistema_prestamos.Modelo.SessionManager

class UserDashboardActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var btnLogout: Button
    private lateinit var tvWelcome: TextView

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sessionManager = SessionManager(applicationContext)

        val currentRole = sessionManager.getUserRole()?.lowercase()

        // --- VERIFICACIÓN DE ROL CRÍTICA ---
        // Redirigimos si no está logeado o si por alguna razón es un rol que no debería estar aquí
        if (currentRole == null || currentRole == "administrador") {
            sessionManager.logout()
            startActivity(Intent(this, login::class.java))
            finish()
            return
        }
        // ------------------------------------

        recyclerView = findViewById(R.id.rv_prestamos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Inicialización de Vistas
        tvWelcome = findViewById(R.id.tv_user_welcome)
        btnLogout = findViewById(R.id.btn_user_logout)


        tvWelcome.text = "Bienvenido, rol: ${currentRole.uppercase()}"

        // Lógica específica para usuarios (ver préstamos, buscar equipos, etc.)...

        // Botón de Logout
        btnLogout.setOnClickListener {
            logoutUser()
        }
    }

    private fun logoutUser() {
        sessionManager.logout()
        startActivity(Intent(this, login::class.java))
        finish()
    }
}
package com.example.sistema_prestamos.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistema_prestamos.Modelo.SessionManager
import com.example.sistema_prestamos.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserDashboardActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var tvWelcome: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomNav: BottomNavigationView // <--- Nueva referencia al menú

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
        if (currentRole == null || currentRole == "administrador") {
            sessionManager.logout()
            startActivity(Intent(this, login::class.java))
            finish()
            return
        }

        // Inicialización de Vistas
        recyclerView = findViewById(R.id.rv_prestamos)
        tvWelcome = findViewById(R.id.tv_user_welcome)
        bottomNav = findViewById(R.id.bottom_navigation) // <--- Enlazamos con el XML

        // Configuración básica
        recyclerView.layoutManager = LinearLayoutManager(this)
        tvWelcome.text = "Bienvenido, ${currentRole.uppercase()}"

        // --- LÓGICA DEL MENÚ DE NAVEGACIÓN ---
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_noticias -> {
                    // Ya estamos en Inicio (Dashboard), no hacemos nada o recargamos lista
                    true
                }
                R.id.nav_prestamos -> {
                    // Ya estamos en Inicio (Dashboard), no hacemos nada o recargamos lista
                    true
                }
                R.id.nav_catalogo -> {
                    // Navegar al Catálogo
                    val intent = Intent(this, CatalogoViewpoint::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_logout -> {
                    // Cerrar Sesión
                    logoutUser()
                    true
                }
                else -> false
            }
        }

        // Aquí puedes llamar a tu función para cargar los préstamos en el RecyclerView
        // cargarPrestamos()
    }

    private fun logoutUser() {
        sessionManager.logout()
        startActivity(Intent(this, login::class.java))
        finish()
    }
}
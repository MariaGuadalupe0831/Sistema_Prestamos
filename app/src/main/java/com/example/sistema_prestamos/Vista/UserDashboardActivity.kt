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
        val currentRole = sessionManager.getUserRole()

        // --- 1. Verificación de Seguridad ---
        if (!sessionManager.isLoggedIn()) {
            startActivity(Intent(this, login::class.java))
            finish()
            return
        }

        // --- 2. Inicialización de Vistas ---
        tvWelcome = findViewById(R.id.tv_user_welcome) // Asumiendo que tienes un TextView de bienvenida
        btnLogout = findViewById(R.id.btn_user_logout) // Asumiendo que tienes un botón de logout

        // Inicialización del RecyclerView
        // ¡VERIFICA ESTE ID en tu XML!
        recyclerView = findViewById(R.id.rv_noticias)
        recyclerView.layoutManager = LinearLayoutManager(this)

        tvWelcome.text = "Bienvenido, ${currentRole ?: "Usuario"}"

        // --- 3. Carga de Datos ---
        loadNoticias() // Carga la lista de noticias

        // --- 4. Listener de Logout ---
        btnLogout.setOnClickListener {
            logoutUser()
        }
    }

    private fun loadNoticias() {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getNoticias()

                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null && result.success) {
                        // Conecta la lista de noticias al RecyclerView
                        recyclerView.adapter = NoticaAdapter(result.noticias)
                        Toast.makeText(this@UserDashboardActivity, result.message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@UserDashboardActivity, "No hay noticias disponibles: ${result?.message}", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@UserDashboardActivity, "Error de servidor (${response.code()})", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@UserDashboardActivity, "Error de red: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun logoutUser() {
        sessionManager.logout()
        startActivity(Intent(this, login::class.java))
        finish()
    }
}
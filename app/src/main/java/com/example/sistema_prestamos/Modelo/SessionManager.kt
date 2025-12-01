package com.example.sistema_prestamos.Modelo

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    // Nombre del archivo de SharedPreferences
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    companion object {
        const val PREF_NAME = "MyLoginAppPrefs"
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
        const val KEY_USER_ROL = "userRole"
    }
    // Guarda el estado de la sesión y el rol del usuario
    fun createLoginSession(rol: String) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putString(KEY_USER_ROL, rol.lowercase()) // Aseguramos que se guarde en minúsculas
        editor.apply()
    }
    // Devuelve el rol del usuario
    fun getUserRole(): String? {
        return prefs.getString(KEY_USER_ROL, null)
    }
    // Verifica si el usuario está logeado
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }
    // Cierra la sesión
    fun logout() {
        editor.clear()
        editor.apply()
    }
}
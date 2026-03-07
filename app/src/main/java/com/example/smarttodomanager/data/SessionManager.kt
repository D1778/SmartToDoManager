package com.example.smarttodomanager.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("smart_todo_prefs", Context.MODE_PRIVATE)

    companion object {
        const val KEY_USER_ID = "user_id"
        const val KEY_THEME = "theme_preference"
    }

    fun saveUserId(userId: Int) {
        prefs.edit().putInt(KEY_USER_ID, userId).apply()
    }

    fun getUserId(): Int {
        return prefs.getInt(KEY_USER_ID, -1) // -1 means no user logged in
    }

    fun clearSession() {
        prefs.edit().remove(KEY_USER_ID).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return getUserId() != -1
    }

    fun isDarkModeEnabled(): Boolean {
        return prefs.getBoolean(KEY_THEME, false)
    }

    fun setDarkMode(isDark: Boolean) {
        prefs.edit().putBoolean(KEY_THEME, isDark).apply()
    }
}

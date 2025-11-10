package com.example.playlist_maker_android_rassohinegor.data.preferences

import android.content.Context
import androidx.core.content.edit

class ThemePreferences(context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isDarkTheme(): Boolean = preferences.getBoolean(KEY_DARK_THEME, false)

    fun setDarkTheme(enabled: Boolean) {
        preferences.edit { putBoolean(KEY_DARK_THEME, enabled) }
    }

    private companion object {
        const val PREFS_NAME = "theme_preferences"
        const val KEY_DARK_THEME = "dark_theme"
    }
}
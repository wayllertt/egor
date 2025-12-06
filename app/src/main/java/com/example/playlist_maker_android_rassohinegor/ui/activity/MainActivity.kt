package com.example.playlist_maker_android_rassohinegor.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.rememberNavController
import com.example.playlist_maker_android_rassohinegor.data.preferences.ThemePreferences
import com.example.playlist_maker_android_rassohinegor.ui.navigation.PlaylistHost
import com.example.playlist_maker_android_rassohinegor.ui.theme.PlaylistmakerandroidRassohinEgorTheme
import com.example.playlist_maker_android_rassohinegor.creator.Creator

class MainActivity : ComponentActivity() {
    private lateinit var themePreferences: ThemePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Creator.init(applicationContext)
        themePreferences = ThemePreferences(this)
        AppCompatDelegate.setDefaultNightMode(
            if (themePreferences.isDarkTheme()) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )

        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(themePreferences.isDarkTheme()) }

            PlaylistmakerandroidRassohinEgorTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()

                PlaylistHost(
                    navController = navController,
                    isDarkTheme = isDarkTheme,
                    onThemeChange = { enabled ->
                        if (isDarkTheme == enabled) return@PlaylistHost
                        themePreferences.setDarkTheme(enabled)
                        isDarkTheme = enabled
                        AppCompatDelegate.setDefaultNightMode(
                            if (enabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
                        )
                    },
                )
            }
        }
    }
}
package com.example.playlist_maker_android_rassohinegor.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.playlist_maker_android_rassohinegor.ui.navigation.PlaylistHost
import com.example.playlist_maker_android_rassohinegor.R
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.playlist_maker_android_rassohinegor.data.preferences.ThemePreferences
import com.example.playlist_maker_android_rassohinegor.ui.theme.PlaylistmakerandroidRassohinEgorTheme

class MainActivity : ComponentActivity() {
    private lateinit var themePreferences: ThemePreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

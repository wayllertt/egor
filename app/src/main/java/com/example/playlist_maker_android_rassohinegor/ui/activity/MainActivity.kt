package com.example.playlist_maker_android_rassohinegor.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import com.example.playlist_maker_android_rassohinegor.PlaylistHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Основная тема приложения
            MaterialTheme {
                // Контроллер навигации
                val navController = rememberNavController()
                // Главный хост, где прописаны все экраны
                PlaylistHost(navController = navController)
            }
        }
    }
}

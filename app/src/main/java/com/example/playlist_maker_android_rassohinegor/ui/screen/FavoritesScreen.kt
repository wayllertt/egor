package com.example.playlist_maker_android_rassohinegor.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

class FavoritesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FavoritesScreen(onBack = { finish() })
        }
    }
}

@Composable
fun FavoritesScreen(onBack: () -> Unit) {

}
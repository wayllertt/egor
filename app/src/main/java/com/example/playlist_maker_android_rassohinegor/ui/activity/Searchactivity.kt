package com.example.playlist_maker_android_rassohinegor.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.playlist_maker_android_rassohinegor.ui.navigation.SearchRoute


class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchRoute(onBack = { finish() })
        }
    }
}
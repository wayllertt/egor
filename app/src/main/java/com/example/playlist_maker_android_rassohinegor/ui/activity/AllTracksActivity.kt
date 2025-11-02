package com.example.playlist_maker_android_rassohinegor.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
//import com.example.playlist_maker_android_rassohinegor.ui.tracks.AllTracksScreen
//
//class AllTracksActivity : ComponentActivity() {
//
//    private val viewModel by viewModels<SearchViewModel> {
//        SearchViewModel.Companion.getViewModelFactory()
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            MaterialTheme {
//                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
//                    AllTracksScreen(
//                        modifier = Modifier.Companion.padding(innerPadding),
//                        viewModel = viewModel
//                    )
//                }
//            }
//        }
//    }
//}
package com.example.playlist_maker_android_rassohinegor

sealed class AppScreen(val route: String) {
    data object Main : AppScreen("main")
    data object Search : AppScreen("search")
    data object Settings : AppScreen("settings")
}
package com.example.playlist_maker_android_rassohinegor

sealed class AppScreen(val route: String) {
    object Main : AppScreen("main")
    object Search : AppScreen("search")
    object Settings : AppScreen("settings")

    object Tracks : AppScreen("tracks")
}

package com.example.playlist_maker_android_rassohinegor.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.playlist_maker_android_rassohinegor.ui.MainScreen
import com.example.playlist_maker_android_rassohinegor.ui.activity.SearchScreen
import com.example.playlist_maker_android_rassohinegor.ui.activity.SettingsScreen
//import com.example.playlist_maker_android_rassohinegor.ui.tracks.AllTracksScreen

private fun singleTop() = navOptions { launchSingleTop = true }

@Composable
fun PlaylistHost(navController: NavHostController) {
    // не трогаем navController.graph — графа ещё нет
    val navigateUp = remember(navController) { { navController.popBackStack(); Unit } }

    val navigateToMain = remember(navController) {
        {
            navController.popBackStack(route = AppScreen.Main.route, inclusive = false)
            navController.navigate(AppScreen.Main.route, singleTop())
        }
    }
    val navigateToSearch = remember(navController) {
        { navController.navigate(AppScreen.Search.route, singleTop()) }
    }
    val navigateToSettings = remember(navController) {
        { navController.navigate(AppScreen.Settings.route, singleTop()) }
    }

    NavHost(
        navController = navController,
        startDestination = AppScreen.Main.route
    ) {
        composable(AppScreen.Main.route) {
            MainScreen(
                onOpenSearch = navigateToSearch,
                onOpenSettings = navigateToSettings,
            )
        }
        composable(AppScreen.Search.route) { SearchScreen(onBack = navigateUp) }
        composable(AppScreen.Settings.route) { SettingsScreen(onBack = navigateUp) }
    }
}

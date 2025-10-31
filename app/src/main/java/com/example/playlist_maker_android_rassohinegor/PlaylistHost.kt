package com.example.playlist_maker_android_rassohinegor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions

private fun singleTop() = navOptions { launchSingleTop = true }

@Composable
fun PlaylistHost(navController: NavHostController) {

    val navigateUp = remember(navController) {
        { navController.navigateUp(); Unit }
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
                onOpenSettings = navigateToSettings
            )
        }
        composable(AppScreen.Search.route) {
            SearchScreen(onBack = navigateUp)
        }
        composable(AppScreen.Settings.route) {
            SettingsScreen(onBack = navigateUp)
        }
    }
}
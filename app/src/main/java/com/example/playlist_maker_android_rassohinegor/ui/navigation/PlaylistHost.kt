package com.example.playlist_maker_android_rassohinegor.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.playlist_maker_android_rassohinegor.ui.screen.FavoritesScreen
import com.example.playlist_maker_android_rassohinegor.ui.screen.MainScreen
import com.example.playlist_maker_android_rassohinegor.ui.screen.SettingsScreen
import com.example.playlist_maker_android_rassohinegor.ui.screen.SearchRoute
import com.example.playlist_maker_android_rassohinegor.ui.screen.NewPlaylistScreen
import com.example.playlist_maker_android_rassohinegor.ui.screen.PlaylistsScreen
import com.example.playlist_maker_android_rassohinegor.ui.screen.TrackDetailsScreen
import com.example.playlist_maker_android_rassohinegor.ui.tracks.TracksScreen

private fun singleTop() = navOptions { launchSingleTop = true }

@Composable
fun PlaylistHost(
    navController: NavHostController,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
) {
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
    val navigateToFavorites = remember(navController) {
        { navController.navigate(AppScreen.Favorites.route, singleTop()) }
    }
    val navigateToTracks = remember(navController) {
        { navController.navigate(AppScreen.Tracks.route, singleTop()) }
    }
    val navigateToPlaylists = remember(navController) {
        { navController.navigate(AppScreen.Playlists.route, singleTop()) }
    }
    val navigateToNewPlaylist = remember(navController) {
        { navController.navigate(AppScreen.NewPlaylist.route) }
    }
    val navigateToTrackDetails = remember(navController) {
        { trackId: Long -> navController.navigate("${AppScreen.TrackDetails.route}/$trackId") }
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
                onOpenFavorites = navigateToFavorites,
                onOpenSettings = navigateToSettings,
                onOpenPlaylists = navigateToPlaylists,
            )
        }
        composable(AppScreen.Search.route) { SearchRoute(onBack = navigateUp, onTrackClick = navigateToTrackDetails) }
        composable(AppScreen.Favorites.route) {
            FavoritesScreen(onBack = navigateUp, onTrackClick = navigateToTrackDetails)
        }
        composable(AppScreen.Tracks.route) { TracksScreen(onBack = navigateUp, onTrackClick = navigateToTrackDetails) }
        composable(AppScreen.Playlists.route) {
            PlaylistsScreen(
                onBack = navigateUp,
                onAddNewPlaylist = navigateToNewPlaylist,
            )
        }
        composable(AppScreen.NewPlaylist.route) {
            NewPlaylistScreen(onBack = navigateUp)
        }
        composable(
            route = "${AppScreen.TrackDetails.route}/{trackId}",
            arguments = listOf(navArgument("trackId") { type = NavType.LongType })
        ) { backStackEntry ->
            val trackId = backStackEntry.arguments?.getLong("trackId") ?: 0L
            TrackDetailsScreen(trackId = trackId, onBack = navigateUp)
        }
        composable(AppScreen.Settings.route) {
            SettingsScreen(
                onBack = navigateUp,
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange,
            )
        }
    }
}
package com.example.playlist_maker_android_rassohinegor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlist_maker_android_rassohinegor.domain.api.PlaylistsRepository

class PlaylistViewModelFactory(
    private val playlistsRepository: PlaylistsRepository,
    private val playlistId: Long
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaylistViewModel::class.java)) {
            return PlaylistViewModel(playlistsRepository, playlistId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class ${'$'}{modelClass.name}")
    }
}
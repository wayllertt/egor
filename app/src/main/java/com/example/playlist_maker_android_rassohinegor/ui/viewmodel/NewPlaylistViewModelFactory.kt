package com.example.playlist_maker_android_rassohinegor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlist_maker_android_rassohinegor.domain.api.PlaylistsRepository

class NewPlaylistViewModelFactory(
    private val playlistsRepository: PlaylistsRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewPlaylistViewModel(playlistsRepository) as T
    }
}
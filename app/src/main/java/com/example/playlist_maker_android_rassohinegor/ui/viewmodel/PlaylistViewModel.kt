package com.example.playlist_maker_android_rassohinegor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_rassohinegor.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_rassohinegor.domain.model.Playlist
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class PlaylistViewModel(
    playlistsRepository: PlaylistsRepository,
    playlistId: Long
) : ViewModel() {

    val playlist: StateFlow<Playlist?> = playlistsRepository.getPlaylist(playlistId)
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)
}
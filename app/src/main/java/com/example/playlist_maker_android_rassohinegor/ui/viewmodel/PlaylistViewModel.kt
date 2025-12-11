package com.example.playlist_maker_android_rassohinegor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_rassohinegor.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_rassohinegor.domain.model.Playlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val playlistId: Long
) : ViewModel() {

    val playlist: StateFlow<Playlist?> = playlistsRepository.getPlaylist(playlistId)
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)
    private val _events = MutableSharedFlow<PlaylistEvent>()
    val events: SharedFlow<PlaylistEvent> = _events.asSharedFlow()

    fun deletePlaylist() {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.deletePlaylistById(playlistId)
            _events.emit(PlaylistEvent.Deleted)
        }
    }
}
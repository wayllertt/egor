package com.example.playlist_maker_android_rassohinegor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_rassohinegor.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_rassohinegor.domain.api.TracksRepository
import com.example.playlist_maker_android_rassohinegor.domain.model.Playlist
import com.example.playlist_maker_android_rassohinegor.domain.model.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class TrackDetailsState(
    val track: Track? = null,
    val playlists: List<Playlist> = emptyList(),
)

class TrackDetailsViewModel(
    private val trackId: Long,
    private val playlistsRepository: PlaylistsRepository,
    private val tracksRepository: TracksRepository,
) : ViewModel() {

    private val trackFlow = tracksRepository.getTrackById(trackId)

    val state: StateFlow<TrackDetailsState> = trackFlow
        .combine(playlistsRepository.getAllPlaylists()) { track, playlists ->
            TrackDetailsState(track = track, playlists = playlists)
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, TrackDetailsState())

    fun toggleFavorite() {
        val track = state.value.track ?: return
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.updateTrackFavoriteStatus(track, !track.favorite)
        }
    }

    fun addToPlaylist(playlistId: Long) {
        val track = state.value.track ?: return
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.insertSongToPlaylist(track, playlistId)
        }
    }
}
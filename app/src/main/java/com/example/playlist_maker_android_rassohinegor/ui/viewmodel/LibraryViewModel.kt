package com.example.playlist_maker_android_rassohinegor.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_rassohinegor.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_rassohinegor.domain.api.TracksRepository
import com.example.playlist_maker_android_rassohinegor.domain.model.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val tracksRepository: TracksRepository,
) : ViewModel() {

    val playlists = playlistsRepository.getAllPlaylists()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val favoriteTracks = tracksRepository.getFavoriteTracks()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun createNewPlaylist(namePlaylist: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.addNewPlaylist(namePlaylist, description, null)
        }
    }

    fun insertSongToPlaylist(track: Track, playlistId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.insertSongToPlaylist(track, playlistId)
        }
    }

    fun toggleFavorite(track: Track, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.updateTrackFavoriteStatus(track, isFavorite)
        }
    }

    fun deletePlaylistById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.deleteTracksByPlaylistId(id)
            playlistsRepository.deletePlaylistById(id)
        }
    }

    fun getTrack(trackId: Long): Flow<Track?> {
        return tracksRepository.getTrackById(trackId)
    }
}
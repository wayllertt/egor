package com.example.playlist_maker_android_rassohinegor.data.repository

import com.example.playlist_maker_android_rassohinegor.domain.model.NO_PLAYLIST
import com.example.playlist_maker_android_rassohinegor.domain.model.Playlist
import com.example.playlist_maker_android_rassohinegor.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class DatabaseMock {
    private val historyList = mutableListOf<String>()
    private val tracks = mutableListOf<Track>()
    private val playlists = mutableListOf<Playlist>()

    private val tracksFlow = MutableStateFlow<List<Track>>(emptyList())
    private val playlistsFlow = MutableStateFlow<List<Playlist>>(emptyList())

    fun getHistory(): List<String> {
        return historyList.toList()
    }

    fun addToHistory(word: String) {
        historyList.add(word)
    }

    fun insertTrack(track: Track) {
        tracks.removeIf { it.id == track.id }
        tracks.add(track)
        notifyTracksChanged()
    }

    fun insertTracks(newTracks: List<Track>) {
        newTracks.forEach { insertTrack(it) }
    }

    fun getTrackById(trackId: Long): Flow<Track?> {
        return tracksFlow.map { list -> list.find { it.id == trackId } }
    }

    fun getTrackByNameAndArtist(track: Track): Flow<Track?> = tracksFlow.map { list ->
        list.find { it.trackName == track.trackName && it.artistName == track.artistName }
    }

    fun getFavoriteTracks(): Flow<List<Track>> = tracksFlow.map { list ->
        list.filter { it.favorite }
    }

    fun addNewPlaylist(namePlaylist: String, description: String) {
        playlists.add(
            Playlist(
                id = (playlists.maxOfOrNull { it.id } ?: 0) + 1,
                name = namePlaylist,
                description = description,
                tracks = emptyList(),
            )
        )
        notifyPlaylistsChanged()
    }

    fun deletePlaylistById(id: Long) {
        playlists.removeIf { it.id == id }
        deleteTracksByPlaylistId(id)
        notifyPlaylistsChanged()
    }

    fun deleteTracksByPlaylistId(playlistId: Long) {
        val updated = tracks.map { track ->
            if (track.playlistId == playlistId) track.copy(playlistId = NO_PLAYLIST) else track
        }
        tracks.clear()
        tracks.addAll(updated)
        notifyTracksChanged()
    }

    fun getPlaylist(playlistId: Long): Flow<Playlist?> = playlistsFlow.map { list ->
        list.find { it.id == playlistId }
    }

    fun getAllPlaylists(): Flow<List<Playlist>> = playlistsFlow

    fun insertSongToPlaylist(track: Track, playlistId: Long) {
        insertTrack(track.copy(playlistId = playlistId))
    }

    fun deleteSongFromPlaylist(track: Track) {
        insertTrack(track.copy(playlistId = NO_PLAYLIST))
    }

    fun updateFavoriteStatus(track: Track, isFavorite: Boolean) {
        insertTrack(track.copy(favorite = isFavorite))
    }

    private fun notifyTracksChanged() {
        tracksFlow.value = tracks.toList()
        notifyPlaylistsChanged()
    }

    private fun notifyPlaylistsChanged() {
        val playlistsWithTracks = playlists.map { playlist ->
            playlist.copy(tracks = tracks.filter { it.playlistId == playlist.id })
        }
        playlistsFlow.value = playlistsWithTracks
    }
}
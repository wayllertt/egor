package com.example.playlist_maker_android_rassohinegor.domain.api

import com.example.playlist_maker_android_rassohinegor.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>

    fun getTrackByNameAndArtist(track: Track): Flow<Track?>

    fun getFavoriteTracks(): Flow<List<Track>>

    fun getTrackById(trackId: Long): Flow<Track?>

    suspend fun insertSongToPlaylist(track: Track, playlistId: Long)

    suspend fun deleteSongFromPlaylist(track: Track)

    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)

    suspend fun deleteTracksByPlaylistId(playlistId: Long)
}
package com.example.playlist_maker_android_rassohinegor.data.repository

import com.example.playlist_maker_android_rassohinegor.data.network.request.TracksSearchRequest
import com.example.playlist_maker_android_rassohinegor.data.network.response.TracksSearchResponse
import com.example.playlist_maker_android_rassohinegor.domain.api.NetworkClient
import com.example.playlist_maker_android_rassohinegor.domain.api.TracksRepository
import com.example.playlist_maker_android_rassohinegor.domain.model.Track
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val database: DatabaseMock,
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        delay(1_000)
        val tracks = if (response.resultCode == 200) {
            (response as TracksSearchResponse).results.map { trackDto ->
                val totalSeconds = trackDto.trackTimeMillis / 1_000
                val minutes = totalSeconds / 60
                val seconds = totalSeconds % 60
                val trackTime = "%02d:%02d".format(minutes, seconds)
                Track(
                    trackName = trackDto.trackName,
                    artistName = trackDto.artistName,
                    trackTime = trackTime,
                    artworkUrl = trackDto.artworkUrl100,
                    playlistId = trackDto.playlistId,
                    id = trackDto.id,
                    favorite = trackDto.favorite
                )
            }
        } else {
            emptyList()
        }
        database.insertTracks(tracks)
        return tracks
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return database.getTrackByNameAndArtist(track)
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return database.getFavoriteTracks()
    }

    override fun getTrackById(trackId: Long): Flow<Track?> {
        return database.getTrackById(trackId)
    }

    override suspend fun insertSongToPlaylist(track: Track, playlistId: Long) {
        database.insertSongToPlaylist(track, playlistId)
    }

    override suspend fun deleteSongFromPlaylist(track: Track) {
        database.deleteSongFromPlaylist(track)
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        database.updateFavoriteStatus(track, isFavorite)
    }

    override suspend fun deleteTracksByPlaylistId(playlistId: Long) {
        database.deleteTracksByPlaylistId(playlistId)
    }
}
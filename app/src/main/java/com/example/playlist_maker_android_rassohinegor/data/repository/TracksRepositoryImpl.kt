package com.example.playlist_maker_android_rassohinegor.data.repository

import com.example.playlist_maker_android_rassohinegor.data.db.AppDatabase
import com.example.playlist_maker_android_rassohinegor.data.db.mapper.toDomain
import com.example.playlist_maker_android_rassohinegor.data.db.mapper.toEntity
import com.example.playlist_maker_android_rassohinegor.data.network.request.TracksSearchRequest
import com.example.playlist_maker_android_rassohinegor.data.network.response.TracksSearchResponse
import com.example.playlist_maker_android_rassohinegor.domain.api.NetworkClient
import com.example.playlist_maker_android_rassohinegor.domain.api.TracksRepository
import com.example.playlist_maker_android_rassohinegor.domain.model.Track
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.playlist_maker_android_rassohinegor.domain.model.NO_PLAYLIST
import kotlinx.coroutines.flow.map

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    database: AppDatabase,
) : TracksRepository {

    private val trackDao = database.trackDao()

    override suspend fun searchTracks(expression: String): List<Track> {
        return withContext(Dispatchers.IO) {
            val response = networkClient.doRequest(TracksSearchRequest(expression))
            if (response !is TracksSearchResponse || response.resultCode != 200) {
                throw IOException("Failed to fetch tracks")
            }

            val tracks = response.results.map { trackDto ->
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
            trackDao.upsertTracks(tracks.map { it.toEntity() })
            tracks
        }
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return trackDao.observeTrackByNameAndArtist(track.trackName, track.artistName).map { it?.toDomain() }
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return trackDao.observeFavoriteTracks().map { list -> list.map { it.toDomain() } }
    }

    override fun getTrackById(trackId: Long): Flow<Track?> {
        return trackDao.observeTrackById(trackId).map { it?.toDomain() }
    }

    override suspend fun insertSongToPlaylist(track: Track, playlistId: Long) {
        trackDao.upsertTrack(track.copy(playlistId = playlistId).toEntity())
    }

    override suspend fun deleteSongFromPlaylist(track: Track) {
        trackDao.upsertTrack(track.copy(playlistId = NO_PLAYLIST).toEntity())
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        trackDao.upsertTrack(track.copy(favorite = isFavorite).toEntity())
    }

    override suspend fun deleteTracksByPlaylistId(playlistId: Long) {
        trackDao.deleteTracksByPlaylistId(playlistId)
    }
}
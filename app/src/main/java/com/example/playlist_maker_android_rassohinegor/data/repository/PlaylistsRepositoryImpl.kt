package com.example.playlist_maker_android_rassohinegor.data.repository

import com.example.playlist_maker_android_rassohinegor.data.db.AppDatabase
import com.example.playlist_maker_android_rassohinegor.data.db.entity.PlaylistEntity
import com.example.playlist_maker_android_rassohinegor.data.db.mapper.toDomain
import com.example.playlist_maker_android_rassohinegor.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_rassohinegor.domain.model.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    database: AppDatabase,
) : PlaylistsRepository {
    private val playlistDao = database.playlistDao()
    private val trackDao = database.trackDao()
    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return playlistDao.observePlaylist(playlistId).map { playlistWithTracks ->
            playlistWithTracks?.toDomain()
        }
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistDao.observeAllPlaylists().map { playlists ->
            playlists.map { it.toDomain() }
        }
    }

    override suspend fun addNewPlaylist(name: String, description: String) {
        playlistDao.insertPlaylist(
            PlaylistEntity(
                name = name,
                description = description,
            ),
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        playlistDao.deletePlaylistById(id)
        trackDao.deleteTracksByPlaylistId(id)
    }
}
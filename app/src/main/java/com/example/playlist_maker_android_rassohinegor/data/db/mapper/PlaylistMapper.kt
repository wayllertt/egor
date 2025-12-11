package com.example.playlist_maker_android_rassohinegor.data.db.mapper

import com.example.playlist_maker_android_rassohinegor.data.db.entity.PlaylistEntity
import com.example.playlist_maker_android_rassohinegor.data.db.entity.PlaylistWithTracks
import com.example.playlist_maker_android_rassohinegor.domain.model.Playlist

fun PlaylistWithTracks.toDomain(): Playlist = Playlist(
    id = playlist.id,
    name = playlist.name,
    description = playlist.description,
    coverImageUri = playlist.coverImageUri,
    tracks = tracks.map { it.toDomain() },
)

fun PlaylistEntity.toDomain(): Playlist = Playlist(
    id = id,
    name = name,
    description = description,
    coverImageUri = coverImageUri,
    tracks = emptyList(),
)
package com.example.playlist_maker_android_rassohinegor.data.db.mapper

import com.example.playlist_maker_android_rassohinegor.data.db.entity.TrackEntity
import com.example.playlist_maker_android_rassohinegor.domain.model.Track

fun TrackEntity.toDomain(): Track = Track(
    trackName = trackName,
    artistName = artistName,
    trackTime = trackTime,
    artworkUrl = artworkUrl,
    playlistId = playlistId,
    id = id,
    favorite = favorite,
)

fun Track.toEntity(): TrackEntity = TrackEntity(
    trackName = trackName,
    artistName = artistName,
    trackTime = trackTime,
    artworkUrl = artworkUrl,
    playlistId = playlistId,
    id = id,
    favorite = favorite,
)
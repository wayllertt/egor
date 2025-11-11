package com.example.playlist_maker_android_rassohinegor.data.dto

import com.example.playlist_maker_android_rassohinegor.domain.model.NO_PLAYLIST

data class TrackDto(
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val playlistId: Long = NO_PLAYLIST,
    val id: Long,
    val favorite: Boolean
)
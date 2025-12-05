package com.example.playlist_maker_android_rassohinegor.domain.model

data class Track(
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl: String? = null,
    val playlistId: Long = NO_PLAYLIST,
    val id: Long,
    val favorite: Boolean
)
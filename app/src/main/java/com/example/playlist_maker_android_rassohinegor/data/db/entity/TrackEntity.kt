package com.example.playlist_maker_android_rassohinegor.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl: String? = null,
    val playlistId: Long,
    @PrimaryKey val id: Long,
    val favorite: Boolean,
)

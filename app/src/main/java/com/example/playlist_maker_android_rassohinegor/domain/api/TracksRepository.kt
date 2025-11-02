package com.example.playlist_maker_android_rassohinegor.domain.api

import com.example.playlist_maker_android_rassohinegor.domain.model.Track

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}
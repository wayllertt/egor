package com.example.playlist_maker_android_rassohinegor.domain.interactor

import com.example.playlist_maker_android_rassohinegor.domain.model.Track

interface TrackSearchInteractor {
    suspend fun getAllTracks(): List<Track>
    suspend fun searchTracks(expression: String): List<Track>
}

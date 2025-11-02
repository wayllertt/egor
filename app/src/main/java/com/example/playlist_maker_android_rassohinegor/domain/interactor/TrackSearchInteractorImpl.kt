package com.example.playlist_maker_android_rassohinegor.domain.interactor

import com.example.playlist_maker_android_rassohinegor.data.network.request.TracksSearchRequest
import com.example.playlist_maker_android_rassohinegor.data.network.response.TracksSearchResponse
import com.example.playlist_maker_android_rassohinegor.domain.api.NetworkClient
import com.example.playlist_maker_android_rassohinegor.domain.api.TracksRepository
import com.example.playlist_maker_android_rassohinegor.domain.model.Track
import kotlinx.coroutines.delay

class TrackSearchInteractorImpl(
private val repository: TracksRepository,
) : TrackSearchInteractor {

    override suspend fun getAllTracks(): List<Track> {
        return repository.searchTracks("")
    }
    override suspend fun searchTracks(expression: String): List<Track> {
        return repository.searchTracks(expression)
    }
}
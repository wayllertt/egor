package com.example.playlist_maker_android_rassohinegor.domain.interactor

import com.example.playlist_maker_android_rassohinegor.domain.model.Track
import com.example.playlist_maker_android_rassohinegor.domain.repository.TracksRepository

class TrackSearchInteractorImpl(
    private val repository: TracksRepository
) : TrackSearchInteractor {

    override suspend fun getAllTracks(): List<Track> =
        repository.searchTracks(expression = "")

    override suspend fun searchTracks(expression: String): List<Track> =
        repository.searchTracks(expression)
}

package com.example.playlist_maker_android_rassohinegor.data.repository

import com.example.playlist_maker_android_rassohinegor.data.dto.TracksSearchRequest
import com.example.playlist_maker_android_rassohinegor.data.network.TracksSearchResponse
import com.example.playlist_maker_android_rassohinegor.domain.model.Track
import com.example.playlist_maker_android_rassohinegor.domain.network.NetworkClient
import com.example.playlist_maker_android_rassohinegor.domain.repository.TracksRepository
import kotlinx.coroutines.delay

class TracksRepositoryImpl(
    private val networkClient: NetworkClient
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        delay(1000) // эмуляция сетевой задержки
        return if (response.resultCode == 200) {
            (response as TracksSearchResponse).results.map { dto ->
                val totalSec = dto.trackTimeMillis / 1000
                val min = totalSec / 60
                val sec = totalSec % 60
                val trackTime = "%02d:%02d".format(min, sec)
                Track(dto.trackName, dto.artistName, trackTime)
            }
        } else {
            emptyList()
        }
    }
}

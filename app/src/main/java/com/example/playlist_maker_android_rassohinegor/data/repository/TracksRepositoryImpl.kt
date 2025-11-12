package com.example.playlist_maker_android_rassohinegor.data.repository

import com.example.playlist_maker_android_rassohinegor.data.network.request.TracksSearchRequest
import com.example.playlist_maker_android_rassohinegor.data.network.response.TracksSearchResponse
import com.example.playlist_maker_android_rassohinegor.domain.api.NetworkClient
import com.example.playlist_maker_android_rassohinegor.domain.api.TracksRepository
import com.example.playlist_maker_android_rassohinegor.domain.model.Track
import kotlinx.coroutines.delay

class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        delay(1_000)
        return if (response.resultCode == 200) {
            (response as TracksSearchResponse).results.map { trackDto ->
                val totalSeconds = trackDto.trackTimeMillis / 1_000
                val minutes = totalSeconds / 60
                val seconds = totalSeconds % 60
                val trackTime = "%02d:%02d".format(minutes, seconds)
                Track(
                    trackName = trackDto.trackName,
                    artistName = trackDto.artistName,
                    trackTime = trackTime,
                    playlistId = trackDto.playlistId,
                    id = trackDto.id,
                    favorite = trackDto.favorite
                )
            }
        } else {
            emptyList()
        }
    }
}
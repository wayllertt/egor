package com.example.playlist_maker_android_rassohinegor.data.network

import com.example.playlist_maker_android_rassohinegor.data.dto.TrackDto
import com.example.playlist_maker_android_rassohinegor.data.network.request.TracksSearchRequest
import com.example.playlist_maker_android_rassohinegor.data.network.response.BaseResponse
import com.example.playlist_maker_android_rassohinegor.data.network.response.TracksSearchResponse
import com.example.playlist_maker_android_rassohinegor.domain.api.NetworkClient
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class RetrofitNetworkClient : NetworkClient {

    override fun doRequest(dto: Any): BaseResponse {
        val request = dto as TracksSearchRequest
        val url = buildUrl(request.expression)

        val connection = url.openConnection() as HttpURLConnection
        return try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 5_000
            connection.readTimeout = 5_000

            val code = connection.responseCode
            if (code != HttpURLConnection.HTTP_OK) {
                throw IOException("Network request failed with code: $code")
            }

            val responseBody = connection.inputStream.use { stream ->
                BufferedReader(InputStreamReader(stream)).readText()
            }
            val tracks = parseTracks(responseBody)
            TracksSearchResponse(tracks).apply { resultCode = code }
        } finally {
            connection.disconnect()
        }
    }

    private fun buildUrl(expression: String): URL {
        val encodedTerm = URLEncoder.encode(expression, Charsets.UTF_8.name())
        val urlString = "https://itunes.apple.com/search?term=$encodedTerm&entity=song"
        return URL(urlString)
    }

    private fun parseTracks(responseBody: String): List<TrackDto> {
        val json = JSONObject(responseBody)
        val results = json.getJSONArray("results")
        val tracks = mutableListOf<TrackDto>()

        for (index in 0 until results.length()) {
            val item = results.getJSONObject(index)
            val track = TrackDto(
                trackName = item.optString("trackName"),
                artistName = item.optString("artistName"),
                trackTimeMillis = item.optLong("trackTimeMillis").toInt(),
                artworkUrl100 = item.optString("artworkUrl100"),
                id = item.optLong("trackId"),
                favorite = false,
            )
            tracks.add(track)
        }

        return tracks
    }
}
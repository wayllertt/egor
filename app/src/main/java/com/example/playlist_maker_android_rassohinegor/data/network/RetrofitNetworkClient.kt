package com.example.playlist_maker_android_rassohinegor.data.network

import com.example.playlist_maker_android_rassohinegor.creator.Storage
import com.example.playlist_maker_android_rassohinegor.data.dto.TracksSearchRequest
import com.example.playlist_maker_android_rassohinegor.domain.network.NetworkClient

class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {
    override fun doRequest(dto: Any): BaseResponse {
        val request = dto as TracksSearchRequest
        val list = storage.search(request.expression)
        return TracksSearchResponse(list).apply { resultCode = 200 }
    }
}

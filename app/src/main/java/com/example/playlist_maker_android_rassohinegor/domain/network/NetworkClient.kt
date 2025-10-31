package com.example.playlist_maker_android_rassohinegor.domain.network

import com.example.playlist_maker_android_rassohinegor.data.network.BaseResponse

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}

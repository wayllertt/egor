package com.example.playlist_maker_android_rassohinegor.domain.api

import com.example.playlist_maker_android_rassohinegor.data.network.response.BaseResponse

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}
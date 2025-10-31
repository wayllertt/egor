package com.example.playlist_maker_android_rassohinegor.creator

import com.example.playlist_maker_android_rassohinegor.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android_rassohinegor.data.repository.TracksRepositoryImpl
import com.example.playlist_maker_android_rassohinegor.domain.repository.TracksRepository
import com.example.playlist_maker_android_rassohinegor.domain.network.NetworkClient

object Creator {
    private val storage by lazy { Storage() }
    private val networkClient: NetworkClient by lazy { RetrofitNetworkClient(storage) }
    val tracksRepository: TracksRepository by lazy { TracksRepositoryImpl(networkClient) }
}

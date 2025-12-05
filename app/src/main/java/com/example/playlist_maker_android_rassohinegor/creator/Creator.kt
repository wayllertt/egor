package com.example.playlist_maker_android_rassohinegor.creator

import androidx.lifecycle.ViewModelProvider
import com.example.playlist_maker_android_rassohinegor.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android_rassohinegor.data.repository.DatabaseMock
import com.example.playlist_maker_android_rassohinegor.data.repository.PlaylistsRepositoryImpl
import com.example.playlist_maker_android_rassohinegor.data.repository.TracksRepositoryImpl
import com.example.playlist_maker_android_rassohinegor.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_rassohinegor.domain.api.TracksRepository
import com.example.playlist_maker_android_rassohinegor.ui.tracks.TracksViewModelFactory
import com.example.playlist_maker_android_rassohinegor.ui.viewmodel.LibraryViewModelFactory
import com.example.playlist_maker_android_rassohinegor.ui.viewmodel.PlaylistViewModelFactory
import com.example.playlist_maker_android_rassohinegor.ui.viewmodel.TrackDetailsViewModelFactory

object Creator {
    private val storage by lazy { Storage() }
    private val networkClient by lazy { RetrofitNetworkClient(storage) }
    private val database by lazy { DatabaseMock() }

    private val tracksRepository: TracksRepository by lazy {
        TracksRepositoryImpl(networkClient, database)
    }
    private val playlistsRepository: PlaylistsRepository by lazy {
        PlaylistsRepositoryImpl(database)
    }

    fun provideTracksRepository(): TracksRepository = tracksRepository
    fun providePlaylistsRepository(): PlaylistsRepository = playlistsRepository

    fun provideSearchViewModelFactory(): ViewModelProvider.Factory {
        return SearchViewModelFactory(tracksRepository)
    }

    fun provideTracksViewModelFactory(): TracksViewModelFactory {
        return TracksViewModelFactory(tracksRepository)
    }

    fun provideLibraryViewModelFactory(): LibraryViewModelFactory {
        return LibraryViewModelFactory(playlistsRepository, tracksRepository)
    }

    fun providePlaylistViewModelFactory(playlistId: Long): PlaylistViewModelFactory {
        return PlaylistViewModelFactory(playlistsRepository, playlistId)
    }

    fun provideTrackDetailsViewModelFactory(trackId: Long): TrackDetailsViewModelFactory {
        return TrackDetailsViewModelFactory(trackId, playlistsRepository, tracksRepository)
    }
}
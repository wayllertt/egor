package com.example.playlist_maker_android_rassohinegor.creator

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.playlist_maker_android_rassohinegor.data.db.AppDatabase
import com.example.playlist_maker_android_rassohinegor.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android_rassohinegor.data.preferences.SearchHistoryPreferences
import com.example.playlist_maker_android_rassohinegor.data.preferences.searchHistoryDataStore
import com.example.playlist_maker_android_rassohinegor.data.repository.PlaylistsRepositoryImpl
import com.example.playlist_maker_android_rassohinegor.data.repository.SearchHistoryRepositoryImpl
import com.example.playlist_maker_android_rassohinegor.data.repository.TracksRepositoryImpl
import com.example.playlist_maker_android_rassohinegor.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_rassohinegor.domain.api.SearchHistoryRepository
import com.example.playlist_maker_android_rassohinegor.domain.api.TracksRepository
import com.example.playlist_maker_android_rassohinegor.ui.tracks.TracksViewModelFactory
import com.example.playlist_maker_android_rassohinegor.ui.viewmodel.LibraryViewModelFactory
import com.example.playlist_maker_android_rassohinegor.ui.viewmodel.PlaylistViewModelFactory
import com.example.playlist_maker_android_rassohinegor.ui.viewmodel.TrackDetailsViewModelFactory
import com.example.playlist_maker_android_rassohinegor.ui.viewmodel.NewPlaylistViewModelFactory

object Creator {
    private var database: AppDatabase? = null
    private var searchHistoryRepository: SearchHistoryRepository? = null

    private val networkClient by lazy { RetrofitNetworkClient() }

    private val tracksRepository: TracksRepository by lazy {
        TracksRepositoryImpl(networkClient, requireDatabase())
    }
    private val playlistsRepository: PlaylistsRepository by lazy {
        PlaylistsRepositoryImpl(requireDatabase())
    }

    fun init(context: Context) {
        if (database == null) {
            database = AppDatabase.create(context)
        }
        if (searchHistoryRepository == null) {
            val preferences = SearchHistoryPreferences(context.searchHistoryDataStore)
            searchHistoryRepository = SearchHistoryRepositoryImpl(preferences)
        }
    }

    private fun requireDatabase(): AppDatabase {
        return database ?: error("Creator is not initialized. Call init(context) first.")
    }

    fun provideTracksRepository(): TracksRepository = tracksRepository
    fun providePlaylistsRepository(): PlaylistsRepository = playlistsRepository
    fun provideSearchHistoryRepository(): SearchHistoryRepository =
        searchHistoryRepository ?: error("Creator is not initialized. Call init(context) first.")

    fun provideSearchViewModelFactory(): ViewModelProvider.Factory {
        return SearchViewModelFactory(
            historyRepository = provideSearchHistoryRepository(),
            repository = tracksRepository
        )
    }

    fun provideTracksViewModelFactory(): TracksViewModelFactory {
        return TracksViewModelFactory(tracksRepository)
    }

    fun provideLibraryViewModelFactory(): LibraryViewModelFactory {
        return LibraryViewModelFactory(playlistsRepository, tracksRepository)
    }

    fun provideNewPlaylistViewModelFactory(): NewPlaylistViewModelFactory {
        return NewPlaylistViewModelFactory(playlistsRepository)
    }

    fun providePlaylistViewModelFactory(playlistId: Long): PlaylistViewModelFactory {
        return PlaylistViewModelFactory(playlistsRepository, playlistId)
    }

    fun provideTrackDetailsViewModelFactory(trackId: Long): TrackDetailsViewModelFactory {
        return TrackDetailsViewModelFactory(trackId, playlistsRepository, tracksRepository)
    }
}
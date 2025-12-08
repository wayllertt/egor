package com.example.playlist_maker_android_rassohinegor.creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlist_maker_android_rassohinegor.domain.api.TracksRepository
import com.example.playlist_maker_android_rassohinegor.ui.navigation.SearchViewModel
import com.example.playlist_maker_android_rassohinegor.domain.api.SearchHistoryRepository

class SearchViewModelFactory(
    private val historyRepository: SearchHistoryRepository,
    private val repository: TracksRepository,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(historyRepository, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class ${'$'}{modelClass.name}")
    }
}
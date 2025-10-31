package com.example.playlist_maker_android_rassohinegor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_rassohinegor.creator.Creator
import com.example.playlist_maker_android_rassohinegor.domain.repository.TracksRepository
import com.example.playlist_maker_android_rassohinegor.ui.tracks.SearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val tracksRepository: TracksRepository
) : ViewModel() {

    private val _allTracksScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val allTracksScreenState = _allTracksScreenState.asStateFlow()

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _allTracksScreenState.update { SearchState.Loading }
            runCatching {
                // Было: tracksRepository.getAllTracks()
                tracksRepository.searchTracks(expression = "")
            }.onSuccess { list ->
                _allTracksScreenState.update { SearchState.Success(foundList = list) }
            }.onFailure { e ->
                _allTracksScreenState.update { SearchState.Error(e.message.orEmpty()) }
            }
        }
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(Creator.tracksRepository) as T
                }
            }
    }
}

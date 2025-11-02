package com.example.playlist_maker_android_rassohinegor.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_rassohinegor.domain.api.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchViewModel(
    private val repository: TracksRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<SearchState>(SearchState.Initial)
    val state: StateFlow<SearchState> = _state.asStateFlow()

    fun search(expression: String) {
        val query = expression.trim()
        if (query.isEmpty()) {
            _state.value = SearchState.Initial
            return
        }

        viewModelScope.launch {
            _state.value = SearchState.Searching
            val result = withContext(Dispatchers.IO) {
                runCatching {
                    repository.searchTracks(query)
                }
            }

            result.fold(
                onSuccess = { tracks ->
                    if (tracks.isEmpty()) {
                        _state.value = SearchState.Fail(SearchError.EMPTY_RESULT)
                    } else {
                        _state.value = SearchState.Success(tracks)
                    }
                },
                onFailure = {
                    _state.value = SearchState.Fail(SearchError.NETWORK)
                },
            )
        }
    }

    fun reset() {
        _state.value = SearchState.Initial
    }
}
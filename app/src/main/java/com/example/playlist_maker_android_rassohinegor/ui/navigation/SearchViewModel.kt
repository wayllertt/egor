package com.example.playlist_maker_android_rassohinegor.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_rassohinegor.domain.api.TracksRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.playlist_maker_android_rassohinegor.domain.api.SearchHistoryRepository

class SearchViewModel(
    private val historyRepository: SearchHistoryRepository,
    private val repository: TracksRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<SearchState>(SearchState.Initial)
    val state: StateFlow<SearchState> = _state.asStateFlow()

    private val _history = MutableStateFlow<List<String>>(emptyList())
    val history: StateFlow<List<String>> = _history.asStateFlow()

    private var searchJob: Job? = null
    private var lastQuery: String? = null

    init {
        loadHistory()
    }

    fun search(expression: String) {
        val query = expression.trim()
        if (query.isEmpty()) {
            reset()
            return
        }

        lastQuery = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _state.value = SearchState.Searching
            runCatching { repository.searchTracks(query) }
                .onSuccess { tracks ->
                    historyRepository.addEntry(query)
                    loadHistory()
                    _state.value = if (tracks.isEmpty()) {
                        SearchState.Fail(SearchError.EMPTY_RESULT)
                    } else {
                        SearchState.Success(tracks)
                    }
                }
                .onFailure {
                    _state.value = SearchState.Fail(SearchError.NETWORK)
                }
        }
    }

    fun retry() {
        lastQuery?.let { search(it) }
    }

    fun reset() {
        searchJob?.cancel()
        _state.value = SearchState.Initial
        lastQuery = null
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _history.value = historyRepository.getEntries()
        }
    }
}
package com.example.playlist_maker_android_rassohinegor.ui.tracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_rassohinegor.domain.interactor.TrackSearchInteractor
import com.example.playlist_maker_android_rassohinegor.domain.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface TracksUiState {
    object Loading : TracksUiState
    data class Content(val tracks: List<Track>) : TracksUiState
    data class Error(val message: String) : TracksUiState
}

class TracksViewModel(
    private val interactor: TrackSearchInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<TracksUiState>(TracksUiState.Loading)
    val state: StateFlow<TracksUiState> = _state

    init {
        load()
    }

    fun load() {
        _state.value = TracksUiState.Loading
        viewModelScope.launch {
            runCatching { interactor.getAllTracks() }
                .onSuccess { _state.value = TracksUiState.Content(it) }
                .onFailure { _state.value = TracksUiState.Error(it.message ?: "Unknown error") }
        }
    }
}

package com.example.playlist_maker_android_rassohinegor.ui.tracks

import com.example.playlist_maker_android_rassohinegor.domain.model.Track

sealed class SearchState {
    data object Initial : SearchState()

    data object Loading : SearchState()

    data class Success(val foundList: List<Track>) : SearchState()

    data class Error(val error: String) : SearchState()
}

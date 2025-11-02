package com.example.playlist_maker_android_rassohinegor.ui.navigation

import com.example.playlist_maker_android_rassohinegor.domain.model.Track

sealed interface SearchState {
    data object Initial : SearchState
    data object Searching : SearchState
    data class Success(val tracks: List<Track>) : SearchState
    data class Fail(val reason: SearchError) : SearchState
}

enum class SearchError {
    NETWORK,
    EMPTY_RESULT,
}
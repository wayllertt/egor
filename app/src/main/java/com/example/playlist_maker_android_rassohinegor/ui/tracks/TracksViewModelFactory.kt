package com.example.playlist_maker_android_rassohinegor.ui.tracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlist_maker_android_rassohinegor.domain.interactor.TrackSearchInteractor
import com.example.playlist_maker_android_rassohinegor.domain.interactor.TrackSearchInteractorImpl
import com.example.playlist_maker_android_rassohinegor.domain.api.TracksRepository

class TracksViewModelFactory(
    private val repository: TracksRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val interactor: TrackSearchInteractor = TrackSearchInteractorImpl(repository)
        return TracksViewModel(interactor) as T
    }
}
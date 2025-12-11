package com.example.playlist_maker_android_rassohinegor.ui.viewmodel

sealed interface PlaylistEvent {
    data object Deleted : PlaylistEvent
}
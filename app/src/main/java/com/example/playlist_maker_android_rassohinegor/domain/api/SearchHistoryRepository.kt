package com.example.playlist_maker_android_rassohinegor.domain.api

interface SearchHistoryRepository {
    fun addEntry(word: String)
    suspend fun getEntries(): List<String>
}
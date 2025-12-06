package com.example.playlist_maker_android_rassohinegor.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>,
    private val coroutineScope: CoroutineScope = CoroutineScope(CoroutineName("search-history-preferences") + SupervisorJob()),
) {

    fun addEntry(word: String) {
        if (word.isEmpty()) {
            return
        }

        coroutineScope.launch {
            dataStore.edit { preferences ->
                val historyString = preferences[PREFERENCES_KEY].orEmpty()
                val history = if (historyString.isNotEmpty()) {
                    historyString.split(SEPARATOR).toMutableList()
                } else {
                    mutableListOf()
                }

                history.remove(word)
                history.add(0, word)

                val updated = history.take(MAX_ENTRIES).joinToString(SEPARATOR)
                preferences[PREFERENCES_KEY] = updated
            }
        }
    }

    suspend fun getEntries(): List<String> {
        val preferences = dataStore.data.first()
        val historyString = preferences[PREFERENCES_KEY].orEmpty()
        if (historyString.isEmpty()) return emptyList()
        return historyString.split(SEPARATOR)
    }

    private companion object {
        private const val MAX_ENTRIES = 10
        private const val SEPARATOR = ","
        private val PREFERENCES_KEY = stringPreferencesKey("search_history")
    }
}
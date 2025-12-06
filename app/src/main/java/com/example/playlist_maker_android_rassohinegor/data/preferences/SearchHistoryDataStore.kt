package com.example.playlist_maker_android_rassohinegor.data.preferences

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.searchHistoryDataStore by preferencesDataStore(name = "search_history_store")
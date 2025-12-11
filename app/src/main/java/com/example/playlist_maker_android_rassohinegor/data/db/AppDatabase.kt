package com.example.playlist_maker_android_rassohinegor.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.playlist_maker_android_rassohinegor.data.db.dao.PlaylistDao
import com.example.playlist_maker_android_rassohinegor.data.db.dao.TrackDao
import com.example.playlist_maker_android_rassohinegor.data.db.entity.PlaylistEntity
import com.example.playlist_maker_android_rassohinegor.data.db.entity.TrackEntity

@Database(
    entities = [TrackEntity::class, PlaylistEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun playlistDao(): PlaylistDao

    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "playlist-maker-db",
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
}
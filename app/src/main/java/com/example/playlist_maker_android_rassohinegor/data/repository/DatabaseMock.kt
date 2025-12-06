package com.example.playlist_maker_android_rassohinegor.data.repository

import com.example.playlist_maker_android_rassohinegor.domain.model.NO_PLAYLIST
import com.example.playlist_maker_android_rassohinegor.domain.model.Playlist
import com.example.playlist_maker_android_rassohinegor.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class DatabaseMock {
    private val historyList = mutableListOf<String>()
    private val tracks = mutableListOf<Track>()
    private val playlists = mutableListOf<Playlist>()

    private val tracksFlow = MutableStateFlow<List<Track>>(emptyList())
    private val playlistsFlow = MutableStateFlow<List<Playlist>>(emptyList())

    init {
        seedInitialData()
    }

    fun getHistory(): List<String> {
        return historyList.toList()
    }

    fun addToHistory(word: String) {
        historyList.add(word)
    }

    fun insertTrack(track: Track) {
        tracks.removeIf { it.id == track.id }
        tracks.add(track)
        notifyTracksChanged()
    }

    fun insertTracks(newTracks: List<Track>) {
        newTracks.forEach { insertTrack(it) }
    }

    fun getTrackById(trackId: Long): Flow<Track?> {
        return tracksFlow.map { list -> list.find { it.id == trackId } }
    }

    fun getTrackByNameAndArtist(track: Track): Flow<Track?> = tracksFlow.map { list ->
        list.find { it.trackName == track.trackName && it.artistName == track.artistName }
    }
    fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return playlistsFlow.map { list -> list.find { it.id == playlistId } }
    }

    fun getAllPlaylists(): Flow<List<Playlist>> = playlistsFlow

    fun addNewPlaylist(name: String, description: String) {
        val nextId = (playlists.maxOfOrNull { it.id } ?: 0L) + 1
        playlists.add(
            Playlist(
                id = nextId,
                name = name,
                description = description,
                tracks = emptyList()
            )
        )
        notifyPlaylistsChanged()
    }

    fun deletePlaylistById(id: Long) {
        playlists.removeIf { it.id == id }
        deleteTracksByPlaylistId(id)
        notifyPlaylistsChanged()
    }

    fun getFavoriteTracks(): Flow<List<Track>> = tracksFlow.map { list ->
        list.filter { it.favorite }
    }

    fun insertSongToPlaylist(track: Track, playlistId: Long) {
        insertTrack(track.copy(playlistId = playlistId))
    }

    fun deleteSongFromPlaylist(track: Track) {
        insertTrack(track.copy(playlistId = NO_PLAYLIST))
    }

    fun updateFavoriteStatus(track: Track, isFavorite: Boolean) {
        insertTrack(track.copy(favorite = isFavorite))
    }

    fun deleteTracksByPlaylistId(playlistId: Long) {
        tracks.removeIf { it.playlistId == playlistId }
        notifyTracksChanged()
    }

    private fun notifyTracksChanged() {
        tracksFlow.value = tracks.toList()
        notifyPlaylistsChanged()
    }

    private fun notifyPlaylistsChanged() {
        val playlistsWithTracks = playlists.map { playlist ->
            playlist.copy(tracks = tracks.filter { it.playlistId == playlist.id })
        }
        playlistsFlow.value = playlistsWithTracks
    }

    private fun seedInitialData() {
        if (tracks.isNotEmpty() || playlists.isNotEmpty()) return

        val roadPlaylistId = 1L
        val calmPlaylistId = 2L

        val initialTracks = listOf(
            Track(
                trackName = "Владивосток 2000",
                artistName = "Мумий Троль",
                trackTime = formatTime(158_000),
                artworkUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/3f/36/92/3f36922f-e8c3-1bb4-c0e4-6e7c84b6602e/cover.jpg/100x100bb.jpg",
                playlistId = roadPlaylistId,
                id = 1,
                favorite = false
            ),
            Track(
                trackName = "Группа крови",
                artistName = "Кино",
                trackTime = formatTime(283_000),
                artworkUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/2f/12/50/2f125080-5b8f-3eb3-ae42-2d0cba3c2f01/075679760569.jpg/100x100bb.jpg",
                playlistId = roadPlaylistId,
                id = 2,
                favorite = false
            ),
            Track(
                trackName = "На заре",
                artistName = "Альянс",
                trackTime = formatTime(230_000),
                artworkUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music118/v4/74/8f/ae/748faeab-8ea1-d49f-fe0c-2d0d5a793102/cover.jpg/100x100bb.jpg",
                playlistId = calmPlaylistId,
                id = 6,
                favorite = false
            )
        )

        tracks.addAll(initialTracks)
        playlists.addAll(
            listOf(
                Playlist(
                    id = roadPlaylistId,
                    name = "Дорога",
                    description = "Лучшие треки для долгих поездок",
                    tracks = initialTracks.filter { it.playlistId == roadPlaylistId }
                ),Playlist(
                    id = calmPlaylistId,
                    name = "Спокойствие",
                    description = "Музыка для отдыха",
                    tracks = initialTracks.filter { it.playlistId == calmPlaylistId }
                )
            )
        )

        notifyTracksChanged()
    }

    private fun formatTime(milliseconds: Int): String {
        val totalSeconds = milliseconds / 1_000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }
}


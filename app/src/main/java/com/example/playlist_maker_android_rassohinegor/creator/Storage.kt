package com.example.playlist_maker_android_rassohinegor.creator

import com.example.playlist_maker_android_rassohinegor.data.dto.TrackDto

class Storage {
    private val listTracks = listOf(
        TrackDto(
            trackName = "Владивосток 2000",
            artistName = "Мумий Троль",
            trackTimeMillis = 158_000,
            id = 1,
            favorite = false
        ),
        TrackDto(
            trackName = "Группа крови",
            artistName = "Кино",
            trackTimeMillis = 283_000,
            id = 2,
            favorite = false
        ),
        TrackDto(
            trackName = "Не смотри назад",
            artistName = "Ария",
            trackTimeMillis = 312_000,
            id = 3,
            favorite = false
        ),
        TrackDto(
            trackName = "Звезда по имени Солнце",
            artistName = "Кино",
            trackTimeMillis = 225_000,
            id = 4,
            favorite = false
        ),
        TrackDto(
            trackName = "Лондон",
            artistName = "Аквариум",
            trackTimeMillis = 272_000,
            id = 5,
            favorite = false
        ),
        TrackDto(
            trackName = "На заре",
            artistName = "Альянс",
            trackTimeMillis = 230_000,
            id = 6,
            favorite = false
        ),
        TrackDto(
            trackName = "Перемен",
            artistName = "Кино",
            trackTimeMillis = 296_000,
            id = 7,
            favorite = false
        ),
        TrackDto(
            trackName = "Розовый фламинго",
            artistName = "Сплин",
            trackTimeMillis = 195_000,
            id = 8,
            favorite = false
        ),
        TrackDto(
            trackName = "Танцевать",
            artistName = "Мельница",
            trackTimeMillis = 222_000,
            id = 9,
            favorite = false
        ),
        TrackDto(
            trackName = "Чёрный бумер",
            artistName = "Серега",
            trackTimeMillis = 241_000,
            id = 10,
            favorite = false
        )
    )

    fun search(request: String): List<TrackDto> {
        val normalizedRequest = request.lowercase()
        return listTracks.filter { track ->
            track.trackName.lowercase().contains(normalizedRequest) ||
                    track.artistName.lowercase().contains(normalizedRequest)
        }
    }
}

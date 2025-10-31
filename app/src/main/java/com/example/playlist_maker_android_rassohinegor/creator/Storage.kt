package com.example.playlist_maker_android_rassohinegor.creator

import com.example.playlist_maker_android_rassohinegor.data.dto.TrackDto

class Storage {
    private val listTracks = listOf(
        TrackDto("Владивосток 2000", "Мумий Троль", 158_000),
        TrackDto("Группа крови", "Кино", 283_000),
        TrackDto("Не смотри назад", "Ария", 312_000),
        TrackDto("Звезда по имени Солнце", "Кино", 225_000),
        TrackDto("Лондон", "Аквариум", 272_000),
        TrackDto("На заре", "Альянс", 230_000),
        TrackDto("Перемен", "Кино", 296_000),
        TrackDto("Розовый фламинго", "Сплин", 195_000),
        TrackDto("Танцевать", "Мельница", 222_000),
        TrackDto("Чёрный бумер", "Серега", 241_000),
    )

    fun search(request: String): List<TrackDto> =
        listTracks.filter { it.trackName.lowercase().contains(request.lowercase()) }
}

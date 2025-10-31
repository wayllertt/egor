package com.example.playlist_maker_android_rassohinegor.creator

import com.example.playlist_maker_android_rassohinegor.data.dto.TrackDto
import java.util.Locale

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

    fun search(request: String): List<TrackDto> {
        val q = request.trim().lowercase(Locale.getDefault())
        if (q.isEmpty()) return listTracks

        return listTracks.filter { dto ->
            // сравниваем в ЛОКАЛИ и по началу названия
            val name = dto.trackName.lowercase(Locale.getDefault())
            // если хочешь «по вхождению», замени startsWith на contains
            name.startsWith(q)
                    // + ещё ищем по каждому слову в названии (начало любого слова):
                    || name.split(' ', '-', '—', ':', '(', ')')
                .any { it.startsWith(q) }
        }
    }
}

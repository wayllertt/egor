package com.example.playlist_maker_android_rassohinegor.creator

import com.example.playlist_maker_android_rassohinegor.data.dto.TrackDto

class Storage {
    private val listTracks = listOf(
        TrackDto(
            trackName = "Владивосток 2000",
            artistName = "Мумий Троль",
            trackTimeMillis = 158_000,
            artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/3f/36/92/3f36922f-e8c3-1bb4-c0e4-6e7c84b6602e/cover.jpg/100x100bb.jpg",
            id = 1,
            favorite = false
        ),
        TrackDto(
            trackName = "Группа крови",
            artistName = "Кино",
            trackTimeMillis = 283_000,
            artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/2f/12/50/2f125080-5b8f-3eb3-ae42-2d0cba3c2f01/075679760569.jpg/100x100bb.jpg",
            id = 2,
            favorite = false
        ),
        TrackDto(
            trackName = "Не смотри назад",
            artistName = "Ария",
            trackTimeMillis = 312_000,
            artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/8a/f5/b3/8af5b31f-0e3e-871c-00d1-3c2b3e3b24ce/192641385081_Cover.jpg/100x100bb.jpg",
            id = 3,
            favorite = false
        ),
        TrackDto(
            trackName = "Звезда по имени Солнце",
            artistName = "Кино",
            trackTimeMillis = 225_000,
            artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/d3/c4/5b/d3c45b48-4c83-0113-1323-711edbe2ae3b/cover.jpg/100x100bb.jpg",
            id = 4,
            favorite = false
        ),
        TrackDto(
            trackName = "Лондон",
            artistName = "Аквариум",
            trackTimeMillis = 272_000,
            artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music/3c/1c/68/mzi.mqlgjupw.jpg/100x100bb.jpg",
            id = 5,
            favorite = false
        ),
        TrackDto(
            trackName = "На заре",
            artistName = "Альянс",
            trackTimeMillis = 230_000,
            artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music118/v4/74/8f/ae/748faeab-8ea1-d49f-fe0c-2d0d5a793102/cover.jpg/100x100bb.jpg",
            id = 6,
            favorite = false
        ),
        TrackDto(
            trackName = "Перемен",
            artistName = "Кино",
            trackTimeMillis = 296_000,
            artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music123/v4/f7/68/92/f7689211-ece4-c1d0-6fd0-8a241e86cd61/cover.jpg/100x100bb.jpg",
            id = 7,
            favorite = false
        ),
        TrackDto(
            trackName = "Розовый фламинго",
            artistName = "Сплин",
            trackTimeMillis = 195_000,
            artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music123/v4/1f/5b/23/1f5b23d2-8cd8-8b4c-1e6d-58446bc78c4b/191061506022.jpg/100x100bb.jpg",
            id = 8,
            favorite = false
        ),
        TrackDto(
            trackName = "Танцевать",
            artistName = "Мельница",
            trackTimeMillis = 222_000,
            artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music125/v4/6e/9f/d9/6e9fd916-09d0-6022-d33d-1ef419910a4e/cover.jpg/100x100bb.jpg",
            id = 9,
            favorite = false
        ),
        TrackDto(
            trackName = "Чёрный бумер",
            artistName = "Серега",
            trackTimeMillis = 241_000,
            artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/07/5d/da/075dda05-2293-6b9d-5e37-008a7d97bf78/cover.jpg/100x100bb.jpg",
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
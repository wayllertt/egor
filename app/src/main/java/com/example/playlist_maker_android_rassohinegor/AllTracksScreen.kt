package com.example.playlist_maker_android_rassohinegor.ui.tracks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android_rassohinegor.R
import com.example.playlist_maker_android_rassohinegor.SearchViewModel
import com.example.playlist_maker_android_rassohinegor.domain.model.Track

@Composable
fun TrackListItem(track: Track) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_music),
            contentDescription = "Трек ${track.trackName}"
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(track.trackName, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(2.dp))
            Text(track.artistName, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        }
        Text(track.trackTime)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTrackListItem() {
    TrackListItem(
        Track(
            trackName = "Владивосток 2000",
            artistName = "Мумий Троль",
            trackTime = "2:38"
        )
    )
}

/**
 * Экран списка треков: подписывается на state у SearchViewModel и рисует:
 *  - лоадер при Loading,
 *  - список при Success,
 *  - текст ошибки при Error,
 *  - ничего при Initial (момент до первого fetchData()).
 */
@Composable
fun AllTracksScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.getViewModelFactory())
) {
    val screenState by viewModel.allTracksScreenState.collectAsState()

    LaunchedEffect(Unit) {
        // при первом показе — грузим данные
        viewModel.fetchData()
    }

    when (val s = screenState) {
        is SearchState.Initial -> {
            // можно показать плейсхолдер, оставим пусто
            Spacer(modifier = modifier.fillMaxSize())
        }

        is SearchState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is SearchState.Success -> {
            val tracks = s.foundList
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tracks) { track ->
                    TrackListItem(track = track)
                    Divider(thickness = 0.5.dp)
                }
            }
        }

        is SearchState.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Ошибка: ${s.error}", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

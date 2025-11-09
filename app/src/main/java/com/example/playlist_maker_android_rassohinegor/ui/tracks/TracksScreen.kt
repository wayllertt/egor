package com.example.playlist_maker_android_rassohinegor.ui.tracks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android_rassohinegor.domain.model.Track
import com.example.playlist_maker_android_rassohinegor.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TracksScreen(
    onBack: () -> Unit,
    viewModel: TracksViewModel = viewModel(factory = TracksViewModelFactory())
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = colorResource(id = R.color.screen_background),
        contentColor = colorResource(id = R.color.primary_text),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.tracks_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.description_back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.screen_background),
                    titleContentColor = colorResource(id = R.color.primary_text),
                    navigationIconContentColor = colorResource(id = R.color.primary_text)
                )
            )
        }
    ) { innerPadding ->
        when (val s = state) {
            TracksUiState.Loading -> Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }

            is TracksUiState.Error -> Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(id = R.string.error_loading_tracks),
                        color = colorResource(id = R.color.primary_text)
                    )
                    Spacer(Modifier.height(dimensionResource(id = R.dimen.retry_button_spacing)))
                    Button(onClick = { viewModel.load() }) {
                        Text(text = stringResource(id = R.string.retry))
                    }
                }
            }

            is TracksUiState.Content -> TracksList(
                tracks = s.tracks,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun TracksList(tracks: List<Track>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = dimensionResource(id = R.dimen.search_content_bottom_padding))
    ) {
        items(tracks) { track ->
            TrackRow(track = track)
            Divider()
        }
    }
}

@Composable
private fun TrackRow(track: Track) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.track_row_padding_horizontal),
                vertical = dimensionResource(id = R.dimen.track_row_padding_vertical)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = track.trackName,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.primary_text)
            )
            Spacer(Modifier.height(dimensionResource(id = R.dimen.track_row_spacing)))
            Text(
                text = track.artistName,
                color = colorResource(id = R.color.primary_text).copy(alpha = 0.7f)
            )
        }
        Text(
            text = track.trackTime,
            color = colorResource(id = R.color.primary_text)
        )
    }
}
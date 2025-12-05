package com.example.playlist_maker_android_rassohinegor.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.playlist_maker_android_rassohinegor.R
import com.example.playlist_maker_android_rassohinegor.creator.Creator
import com.example.playlist_maker_android_rassohinegor.domain.model.Playlist
import com.example.playlist_maker_android_rassohinegor.domain.model.Track
import com.example.playlist_maker_android_rassohinegor.ui.viewmodel.LibraryViewModel
import com.example.playlist_maker_android_rassohinegor.ui.viewmodel.PlaylistViewModel

@Composable
fun PlaylistsScreen(
    onBack: () -> Unit,
    onAddNewPlaylist: () -> Unit,
    onPlaylistClick: (Long) -> Unit,
    viewModel: LibraryViewModel = viewModel(factory = Creator.provideLibraryViewModelFactory())
) {
    val playlists by viewModel.playlists.collectAsState()

    Scaffold(
        containerColor = colorResource(id = R.color.screen_background),
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNewPlaylist) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.add_playlist)
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.playlists)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.description_back)
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        if (playlists.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.playlists_empty),
                    color = colorResource(id = R.color.primary_text)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                items(playlists) { playlist ->
                    PlaylistListItem(
                        playlist = playlist,
                        onClick = { onPlaylistClick(playlist.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun PlaylistListItem(
    playlist: Playlist,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                horizontal = dimensionResource(id = R.dimen.screen_padding),
                vertical = dimensionResource(id = R.dimen.track_row_padding_vertical)
            )
    ) {
        Text(text = playlist.name, color = colorResource(id = R.color.primary_text))
        Text(
            text = playlist.description,
            color = colorResource(id = R.color.primary_text).copy(alpha = 0.7f)
        )
        Text(
            text = stringResource(id = R.string.playlist_tracks_count, playlist.tracks.size),
            color = colorResource(id = R.color.primary_text)
        )
    }
}

@Composable
fun PlaylistScreen(
    onBack: () -> Unit,
    onTrackClick: (Long) -> Unit,
    viewModel: PlaylistViewModel
) {
    val playlistState by viewModel.playlist.collectAsState()

    Scaffold(
        containerColor = colorResource(id = R.color.screen_background),
        topBar = {
            TopAppBar(
                title = { Text(text = playlistState?.name ?: stringResource(id = R.string.playlists)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
        val playlist = playlistState
        if (playlist == null) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.playlist_not_found),
                    color = colorResource(id = R.color.primary_text)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(id = R.dimen.screen_padding)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.track_row_spacing))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.track_row_padding_vertical)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_music),
                        contentDescription = null,
                        tint = colorResource(id = R.color.primary_text),
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.playlist_cover_size))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.header_corner_radius)))
                    )
                }

                Text(
                    text = playlist.name,
                    color = colorResource(id = R.color.primary_text),
                    style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = playlist.description,
                    color = colorResource(id = R.color.primary_text).copy(alpha = 0.7f),
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(id = R.string.playlist_tracks_count, playlist.tracks.size),
                    color = colorResource(id = R.color.primary_text),
                    fontWeight = FontWeight.SemiBold
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.search_results_spacing)),
                    contentPadding = PaddingValues(bottom = dimensionResource(id = R.dimen.search_content_bottom_padding))
                ) {
                    items(playlist.tracks) { track ->
                        PlaylistTrackRow(track = track) { onTrackClick(track.id) }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaylistTrackRow(
    track: Track,
    onClick: () -> Unit
) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = dimensionResource(id = R.dimen.track_row_padding_vertical)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.track_row_padding_horizontal)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = track.artworkUrl,
            contentDescription = track.trackName,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.track_cover_size))
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.search_result_corner_radius))),
            placeholder = painterResource(id = R.drawable.ic_music),
            error = painterResource(id = R.drawable.ic_music),
            fallback = painterResource(id = R.drawable.ic_music)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = track.trackName,
                color = colorResource(id = R.color.primary_text),
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = track.artistName,
                color = colorResource(id = R.color.primary_text).copy(alpha = 0.7f),
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            text = track.trackTime,
            color = colorResource(id = R.color.primary_text),
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )
    }
}
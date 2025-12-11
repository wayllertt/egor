package com.example.playlist_maker_android_rassohinegor.ui.screen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.playlist_maker_android_rassohinegor.R
import com.example.playlist_maker_android_rassohinegor.creator.Creator
import com.example.playlist_maker_android_rassohinegor.domain.model.Playlist
import com.example.playlist_maker_android_rassohinegor.domain.model.Track
import com.example.playlist_maker_android_rassohinegor.ui.viewmodel.TrackDetailsViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailsScreen(
    trackId: Long,
    onBack: () -> Unit,
) {
    val viewModel: TrackDetailsViewModel = viewModel(
        factory = Creator.provideTrackDetailsViewModelFactory(trackId)
    )
    val state by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = colorResource(id = R.color.screen_background),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.track_details_title)) },
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
        val track = state.track
        if (track == null) {
            Text(
                text = stringResource(id = R.string.track_not_found),
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.screen_padding)),
                color = colorResource(id = R.color.primary_text)
            )
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.screen_padding)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.track_row_spacing))
            ) {
                AsyncImage(
                    model = track.artworkUrl,
                    contentDescription = stringResource(id = R.string.playlist_cover),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(colorResource(id = R.color.screen_background))
                        .padding(bottom = dimensionResource(id = R.dimen.track_row_spacing)),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_music),
                    error = painterResource(id = R.drawable.ic_music),
                )
                Text(text = track.trackName, color = colorResource(id = R.color.primary_text))
                Text(
                    text = stringResource(id = R.string.track_artist_time, track.artistName, track.trackTime),
                    color = colorResource(id = R.color.primary_text)
                )
                RowActions(
                    track = track,
                    onToggleFavorite = { viewModel.toggleFavorite() },
                    onAddToPlaylist = { showSheet = true }
                )
            }

            if (showSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showSheet = false },
                    sheetState = sheetState
                ) {
                    if (state.playlists.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.playlists_empty),
                            color = colorResource(id = R.color.primary_text),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimensionResource(id = R.dimen.screen_padding))
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimensionResource(id = R.dimen.screen_padding)),
                            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.track_row_spacing))
                        ) {
                            items(state.playlists) { playlist ->
                                PlaylistSheetRow(
                                    playlist = playlist,
                                    onClick = {
                                        viewModel.addToPlaylist(playlist.id)
                                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                                            showSheet = false
                                        }

                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
private fun PlaylistSheetRow(
    playlist: Playlist,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = dimensionResource(id = R.dimen.track_row_padding_vertical)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.drawer_item_icon_spacing))
    ) {
        if (playlist.coverImageUri != null) {
            AsyncImage(
                model = Uri.parse(playlist.coverImageUri),
                contentDescription = stringResource(id = R.string.playlist_cover),
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.track_cover_size))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.search_result_corner_radius))),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_music),
                error = painterResource(id = R.drawable.ic_music)
            )
        } else {
            Icon(
                imageVector = Icons.Filled.PlaylistPlay,
                contentDescription = stringResource(id = R.string.playlist_cover),
                tint = colorResource(id = R.color.primary_text),
                modifier = Modifier.size(dimensionResource(id = R.dimen.track_cover_size))
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = playlist.name,
                color = colorResource(id = R.color.primary_text),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp
            )
            Text(
                text = stringResource(id = R.string.playlist_tracks_count, playlist.tracks.size),
                color = colorResource(id = R.color.grey),
                fontSize = 14.sp
            )
        }
    }
}


@Composable
private fun RowActions(
    track: Track,
    onToggleFavorite: () -> Unit,
    onAddToPlaylist: () -> Unit,
) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.drawer_item_icon_spacing))
    ) {
        IconButton(onClick = onToggleFavorite) {
            Icon(
                imageVector = if (track.favorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = stringResource(id = R.string.favorite_icon_description),
                tint = colorResource(id = R.color.primary_text)
            )
        }
        IconButton(onClick = onAddToPlaylist) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.add_to_playlist),
                tint = colorResource(id = R.color.primary_text)
            )
        }
    }
}
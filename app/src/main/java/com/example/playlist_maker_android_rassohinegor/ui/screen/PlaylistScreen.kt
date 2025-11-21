package com.example.playlist_maker_android_rassohinegor.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android_rassohinegor.R
import com.example.playlist_maker_android_rassohinegor.creator.Creator
import com.example.playlist_maker_android_rassohinegor.domain.model.Playlist
import com.example.playlist_maker_android_rassohinegor.ui.viewmodel.LibraryViewModel

@Composable
fun PlaylistsScreen(
    onBack: () -> Unit,
    onAddNewPlaylist: () -> Unit,
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.screen_background),
                    titleContentColor = colorResource(id = R.color.primary_text),
                    navigationIconContentColor = colorResource(id = R.color.primary_text)
                )
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
                    PlaylistListItem(playlist = playlist)
                }
            }
        }
    }
}

@Composable
fun PlaylistListItem(playlist: Playlist) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.screen_padding),
                vertical = dimensionResource(id = R.dimen.track_row_padding_vertical))
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
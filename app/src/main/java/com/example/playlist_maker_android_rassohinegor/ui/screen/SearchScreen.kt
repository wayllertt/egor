package com.example.playlist_maker_android_rassohinegor.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android_rassohinegor.creator.Creator
import com.example.playlist_maker_android_rassohinegor.R
import com.example.playlist_maker_android_rassohinegor.domain.model.Track
import androidx.compose.foundation.lazy.items
import com.example.playlist_maker_android_rassohinegor.ui.navigation.SearchError
import com.example.playlist_maker_android_rassohinegor.ui.navigation.SearchState
import com.example.playlist_maker_android_rassohinegor.ui.navigation.SearchViewModel

@Composable
fun SearchRoute(
    onBack: () -> Unit,
    onTrackClick: (Long) -> Unit,
    viewModel: SearchViewModel = viewModel(factory = Creator.provideSearchViewModelFactory()),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SearchScreen(
        state = state,
        onBack = onBack,
        onSearch = viewModel::search,
        onReset = viewModel::reset,
        onTrackClick = onTrackClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchState,
    onBack: () -> Unit,
    onSearch: (String) -> Unit,
    onReset: () -> Unit,
    onTrackClick: (Long) -> Unit,
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val fieldColor = MaterialTheme.colorScheme.surfaceVariant
    val context = LocalContext.current

    Scaffold(
        containerColor = colorResource(id = R.color.screen_background),
        contentColor = colorResource(id = R.color.primary_text),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.search_title),
                        fontWeight = FontWeight.Medium,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.description_back),
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.screen_background),
                    titleContentColor = colorResource(id = R.color.primary_text),
                    navigationIconContentColor = colorResource(id = R.color.primary_text)
                )
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(colorResource(id = R.color.screen_background))
                .padding(horizontal = dimensionResource(id = R.dimen.screen_padding)),
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.search_field_top_spacing)))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { newValue ->
                    searchQuery = newValue
                    if (newValue.isBlank()) {
                        onReset()
                    } else {
                        onSearch(newValue) }
                },
                placeholder = { Text(text = stringResource(id = R.string.placeholder)) },
                leadingIcon = {
                    IconButton(
                        onClick = { onSearch(searchQuery) },
                        enabled = searchQuery.isNotBlank(),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(id = R.string.description_search),
                        )
                    }
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = {
                            searchQuery = ""
                            onReset()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = stringResource(id = R.string.description_clear),
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.search_result_corner_radius)),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = fieldColor,
                    unfocusedContainerColor = fieldColor,
                    disabledContainerColor = fieldColor,
                    errorContainerColor = fieldColor,
                    focusedBorderColor = androidx.compose.ui.graphics.Color.Transparent,
                    unfocusedBorderColor = androidx.compose.ui.graphics.Color.Transparent,
                    disabledBorderColor = androidx.compose.ui.graphics.Color.Transparent,
                    errorBorderColor = androidx.compose.ui.graphics.Color.Transparent,
                ),
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.search_field_bottom_spacing)))

            when (state) {
                SearchState.Initial -> Unit
                SearchState.Searching -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = dimensionResource(id = R.dimen.search_content_top_padding)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.search_field_top_spacing)))
                        Text(
                            text = stringResource(id = R.string.loading),
                            color = colorResource(id = R.color.primary_text)
                        )
                    }
                }

                is SearchState.Fail -> {
                    val message = when (state.reason) {
                        SearchError.EMPTY_RESULT -> R.string.error_empty
                        SearchError.NETWORK -> R.string.error_network
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = dimensionResource(id = R.dimen.search_content_top_padding)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(id = message),
                            color = colorResource(id = R.color.primary_text)
                        )
                    }
                }

                is SearchState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = dimensionResource(id = R.dimen.search_content_bottom_padding)),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.search_results_spacing)),
                    ) {
                        items(state.tracks) { track ->
                            TrackRow(
                                track = track,
                                onClick = { onTrackClick(track.id) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TrackRow(
    track: Track,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.search_result_corner_radius)),
            )
            .clickable(onClick = onClick)
            .padding(
                horizontal = dimensionResource(id = R.dimen.search_result_padding_horizontal),
                vertical = dimensionResource(id = R.dimen.search_result_padding_vertical),
            ),
    ) {
        RowWithTime(
            title = track.trackName,
            time = track.trackTime,
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.search_result_artist_spacing)))
        Text(
            text = track.artistName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun RowWithTime(
    title: String,
    time: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = time,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

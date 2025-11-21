package com.example.playlist_maker_android_rassohinegor.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.example.playlist_maker_android_rassohinegor.R
import com.example.playlist_maker_android_rassohinegor.domain.model.Track

@Composable
fun TrackListItem(track: Track, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.track_row_padding_horizontal),
                    vertical = dimensionResource(id = R.dimen.track_row_padding_vertical)
                )
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
        Divider()
    }
}
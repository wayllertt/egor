package com.example.playlist_maker_android_rassohinegor.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_rassohinegor.R

@Composable
fun MainScreen(
    onOpenSearch: () -> Unit,
    onOpenFavorites: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenTracks: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.screen_background))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    colorResource(id = R.color.main_header_background),
                    RoundedCornerShape(
                        bottomStart = dimensionResource(id = R.dimen.header_corner_radius),
                        bottomEnd = dimensionResource(id = R.dimen.header_corner_radius)
                    )
                )
                .padding(
                    vertical = dimensionResource(id = R.dimen.header_padding_vertical),
                    horizontal = dimensionResource(id = R.dimen.header_padding_horizontal)
                )
        ) {
            Text(
                text = stringResource(id = R.string.playlist_maker),
                color = colorResource(id = R.color.main_header_text),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.header_spacing_below)))

        DrawerItem(icon = Icons.Default.Search, text = stringResource(id = R.string.search)) { onOpenSearch() }
        DrawerItem(icon = Icons.Default.PlayArrow, text = stringResource(id = R.string.playlists)) { onOpenTracks() }
        DrawerItem(icon = Icons.Default.FavoriteBorder, text = stringResource(id = R.string.favorites)) { onOpenFavorites() }
        DrawerItem(icon = Icons.Default.Settings, text = stringResource(id = R.string.settings_title)) { onOpenSettings() }
    }
}

@Composable
fun DrawerItem(
    icon: ImageVector,
    text: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick?.invoke() }
            .padding(
                horizontal = dimensionResource(id = R.dimen.drawer_item_padding_horizontal),
                vertical = dimensionResource(id = R.dimen.drawer_item_padding_vertical)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colorResource(id = R.color.primary_text).copy(alpha = 0.85f),
            modifier = Modifier.size(dimensionResource(id = R.dimen.drawer_item_icon_size))
        )

        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.drawer_item_icon_spacing)))

        Text(
            text = text,
            color = colorResource(id = R.color.primary_text).copy(alpha = 0.9f),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
    }
}
package com.example.playlist_maker_android_rassohinegor.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.playlist_maker_android_rassohinegor.ui.navigation.PlaylistHost
import com.example.playlist_maker_android_rassohinegor.R
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            PlaylistHost(navController = navController)
        }
    }
}

@Composable
fun MainScreen(
    onOpenSearch: () -> Unit,
    onOpenPlaylists: () -> Unit,
    onOpenFavorites: () -> Unit,
    onOpenSettings: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.screen_background))
    ) {
        Header(title = stringResource(id = R.string.playlist_maker))
        Spacer(Modifier.height(dimensionResource(id = R.dimen.header_spacing_below)))

        MenuRow(icon = Icons.Default.Search, text = stringResource( R.string.search)) {
            onOpenSearch()
        }
        MenuRow(icon = Icons.Default.PlayArrow, text = stringResource(R.string.playlists)) {
            onOpenPlaylists()
        }
        MenuRow(icon = Icons.Default.FavoriteBorder, text = stringResource(R.string.favorites)) {
            onOpenFavorites()
        }
        MenuRow(icon = Icons.Default.Settings, text = stringResource(R.string.settings_title)) {
            onOpenSettings()
        }
    }
}

@Composable
private fun Header(title: String) {
    Box(
        modifier = Modifier
            .background(
                color = colorResource(id = R.color.main_header_background),
                shape = RoundedCornerShape(
                    bottomStart = dimensionResource(id = R.dimen.header_corner_radius),
                    bottomEnd = dimensionResource(id = R.dimen.header_corner_radius)
                )
            )
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.header_padding_horizontal),
                vertical = dimensionResource(id = R.dimen.header_padding_vertical)
            )
    ) {
        Text(
            text = title,
            color = colorResource(id = R.color.main_header_text),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MenuRow(
    icon: ImageVector,
    text: String,
    onClick: (() -> Unit)? = null
) {
    val click = rememberUpdatedState(newValue = onClick)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { click.value?.invoke() }
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
            modifier = Modifier.size(dimensionResource(id = R.dimen.menu_row_icon_size))
        )

        Spacer(Modifier.width(dimensionResource(id = R.dimen.menu_row_icon_spacing)))

        Text(
            text = text,
            color = colorResource(id = R.color.primary_text).copy(alpha = 0.9f),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
    }
}

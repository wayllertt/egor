package com.example.playlist_maker_android_rassohinegor

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

private val BgPage = Color(0xFFF9F9F9)
private val BgHeader = Color(0xFF3D6EFF)
private val ItemIconTint = Color.Black.copy(alpha = 0.85f)
private val ItemTextTint = Color.Black.copy(alpha = 0.9f)
private val HeaderCorner = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            PlaylistHost(navController = navController)
        }
    }
}

private data class MenuItemSpec(
    val icon: ImageVector,
    val title: String,
    val onClick: () -> Unit
)

@Composable
fun MainScreen(
    onOpenSearch: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val context = LocalContext.current

    val items = listOf(
        MenuItemSpec(Icons.Default.Search, "Поиск") { onOpenSearch() },
        MenuItemSpec(Icons.Default.PlayArrow, "Плейлисты") {
            Toast.makeText(context, "Кнопка нажата", Toast.LENGTH_SHORT).show()
        },
        MenuItemSpec(Icons.Default.FavoriteBorder, "Избранное") {
            Toast.makeText(context, "Кнопка нажата", Toast.LENGTH_SHORT).show()
        },
        MenuItemSpec(Icons.Default.Settings, "Настройки") { onOpenSettings() }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgPage)
    ) {
        MainHeader()
        Spacer(Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 6.dp)
        ) {
            items(items) { item ->
                DrawerItem(icon = item.icon, text = item.title, onClick = item.onClick)
            }
        }
    }
}

@Composable
private fun MainHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(BgHeader, HeaderCorner)
            .padding(vertical = 20.dp, horizontal = 16.dp)
    ) {
        Text(
            text = "Playlist maker",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
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
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = ItemIconTint,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = ItemTextTint,fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
    }
}
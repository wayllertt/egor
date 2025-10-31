package com.example.playlist_maker_android_rassohinegor

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.foundation.background
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.filled.Search
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import android.widget.Toast

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
            }
        }

    @Preview
    @Composable
    fun MainScreen() {

        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9F9F9))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF3D6EFF), RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                    .padding(vertical = 20.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = "Playlist maker",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            DrawerItem(icon = Icons.Default.Search, text = "Поиск") {
                val intent = Intent(context, Searchactivity::class.java)
                context.startActivity(intent)
            }
            DrawerItem(icon = Icons.Default.PlayArrow, text = "Плейлисты") {
                Toast.makeText(context, "Нажато", Toast.LENGTH_LONG).show()
            }
            DrawerItem(icon = Icons.Default.FavoriteBorder, text = "Избранное") {
                Toast.makeText(context, "Нажато", Toast.LENGTH_SHORT).show()
            }
            DrawerItem(icon = Icons.Default.Settings, text = "Настройки") {
                val intent = Intent(context, Settingsactivity::class.java)
                context.startActivity(intent)
            }

        }
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
            .clickable { onClick?.invoke() }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black.copy(alpha = 0.85f),
            modifier = Modifier.size(22.dp)
        )

        Text(
            text = text,
            color = Color.Black.copy(alpha = 0.9f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )

    }
}

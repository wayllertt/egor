package com.example.playlist_maker_android_rassohinegor.ui.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_rassohinegor.R
import com.example.playlist_maker_android_rassohinegor.R

class Settingsactivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SettingsScreen(onBack = { finish() }) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    val shareText = stringResource(R.string.share_text)
    val emailTo = stringResource(R.string.dev_email_to)
    val emailSubject = stringResource(R.string.dev_email_subject)
    val emailBody = stringResource(R.string.dev_email_body)
    val offerUrl = stringResource(R.string.offer_url)

    val onShare = remember {
        {
            val send = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            val chooser = Intent.createChooser(
                send,
                context.getString(R.string.share_chooser_title)
            )
            try {
                context.startActivity(chooser)
            } catch (_: ActivityNotFoundException) {
                Toast.makeText(
                    context,
                    context.getString(R.string.no_apps_found),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    val onWriteToDevs = remember {
        {
            val mailUri = Uri.parse("mailto:$emailTo")
            val emailIntent = Intent(Intent.ACTION_SENDTO, mailUri).apply {
                putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                putExtra(Intent.EXTRA_TEXT, emailBody)
            }
            try {
                context.startActivity(emailIntent)
            } catch (_: ActivityNotFoundException) {
                Toast.makeText(
                    context,
                    context.getString(R.string.no_mail_clients),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    val onOpenOffer = remember {
        {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(offerUrl))
            try {
                context.startActivity(intent)
            } catch (_: ActivityNotFoundException) {
                Toast.makeText(
                    context,
                    context.getString(R.string.no_browser_found),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings_title),
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.white) )

            )
        }
    ) { innerPadding ->
        val items = listOf(
            SettingsItem(
                title = stringResource(R.string.btn_share_app),
                trailing = { Icon(Icons.Filled.Share, contentDescription = null) },
                onClick = onShare
            ),
            SettingsItem(
                title = stringResource(R.string.btn_write_to_devs),
                trailing = { Icon(Icons.Filled.Email, contentDescription = null) },
                onClick = onWriteToDevs
            ),
            SettingsItem(
                title = stringResource(R.string.btn_user_agreement),
                trailing = { Icon(Icons.Filled.ArrowForward, contentDescription = null) },
                onClick = onOpenOffer
            )
        )

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            item { Divider() }

            items(items) { item ->
                SettingRow(
                    text = item.title,
                    trailing = item.trailing,
                    onClick = item.onClick
                )
                Divider()
            }
        }
    }
}

private data class SettingsItem(
    val title: String,
    val trailing: @Composable () -> Unit,
    val onClick: () -> Unit
)

@Composable
private fun SettingRow(
    text: String,
    trailing: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        trailing()
    }
}
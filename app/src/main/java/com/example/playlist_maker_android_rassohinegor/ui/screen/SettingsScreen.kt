package com.example.playlist_maker_android_rassohinegor.ui.screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_rassohinegor.R
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
) {
    val context = LocalContext.current

    val emailTo = stringResource(R.string.dev_email_to)
    val emailSubject = stringResource(R.string.dev_email_subject)
    val emailBody = stringResource(R.string.dev_email_body)
    val offerUrl = stringResource(R.string.offer_url)

    Scaffold(
        containerColor = colorResource(id = R.color.screen_background),
        contentColor = colorResource(id = R.color.primary_text),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings_title),
                        fontWeight = FontWeight.Medium,
                    )
                },
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            SettingsToggleRow(
                text = stringResource(R.string.theme_dark_mode),
                checked = isDarkTheme,
                onCheckedChange = onThemeChange,
            )
            Spacer(Modifier.height(dimensionResource(id = R.dimen.divider_spacing)))

            SettingsActionRow(
                text = stringResource(R.string.btn_share_app),
                onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_text))
                    }
                    val chooser = Intent.createChooser(
                        shareIntent,
                        context.getString(R.string.share_chooser_title),
                    )
                    context.startActivity(chooser)
                },
            ) {
                Icon(imageVector = Icons.Filled.Share, contentDescription = null)
            }

            Spacer(Modifier.height(dimensionResource(id = R.dimen.divider_spacing)))

            SettingsActionRow(
                text = stringResource(R.string.btn_write_to_devs),
                onClick = {
                    val mailUri = Uri.parse("mailto:$emailTo")
                    val emailIntent = Intent(Intent.ACTION_SENDTO, mailUri).apply {
                        putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                        putExtra(Intent.EXTRA_TEXT, emailBody)
                    }
                    context.startActivity(emailIntent)
                },
            ) {
                Icon(imageVector = Icons.Filled.Email, contentDescription = null)
            }

            Spacer(Modifier.height(dimensionResource(id = R.dimen.divider_spacing)))

            SettingsActionRow(
                text = stringResource(R.string.btn_user_agreement),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(offerUrl))
                    context.startActivity(intent)
                },
            ) {
                Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = null)
            }
        }
    }
}

@Composable
private fun SettingsToggleRow(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(
                horizontal = dimensionResource(id = R.dimen.screen_padding),
                vertical = dimensionResource(id = R.dimen.settings_item_padding_vertical),
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = colorResource(id = R.color.primary_text),
            ),
        )
    }
}

@Composable
private fun SettingsActionRow(
    text: String,
    onClick:() -> Unit,
    trailingContent: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                horizontal = dimensionResource(id = R.dimen.screen_padding),
                vertical = dimensionResource(id = R.dimen.settings_item_padding_vertical),
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
        )
        trailingContent()
    }
}

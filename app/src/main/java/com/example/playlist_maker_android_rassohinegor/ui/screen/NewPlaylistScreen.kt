package com.example.playlist_maker_android_rassohinegor.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.playlist_maker_android_rassohinegor.R
import com.example.playlist_maker_android_rassohinegor.creator.Creator
import com.example.playlist_maker_android_rassohinegor.ui.viewmodel.NewPlaylistViewModel
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.TextFieldDefaults



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPlaylistScreen(
    onBack: () -> Unit,
    viewModel: NewPlaylistViewModel = viewModel(factory = Creator.provideNewPlaylistViewModelFactory())
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val coverImageUri by viewModel.coverImageUri.collectAsState()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.setCoverImageUri(it.toString()) }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            imagePickerLauncher.launch("image/*")
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.permission_required),
                Toast.LENGTH_SHORT,
            ).show()
        }
    }

    fun requestImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            imagePickerLauncher.launch("image/*")
        } else {
            val permission = Manifest.permission.READ_EXTERNAL_STORAGE
            when {
                ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED -> {
                    imagePickerLauncher.launch("image/*")
                }

                else -> permissionLauncher.launch(permission)
            }
        }
    }

    Scaffold(
        containerColor = colorResource(id = R.color.screen_background),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.new_playlist_title)) },
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(dimensionResource(id = R.dimen.screen_padding))
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.track_row_spacing)
                )
            ) {
                Text(
                    text = stringResource(id = R.string.select_image),
                    color = colorResource(id = R.color.primary_text)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.header_corner_radius)))
                        .background(colorResource(id = R.color.screen_background).copy(alpha = 0.1f))
                        .clickable { requestImage() },
                    contentAlignment = Alignment.Center
                ) {
                    if (coverImageUri != null) {
                        AsyncImage(
                            model = Uri.parse(coverImageUri),
                            contentDescription = stringResource(id = R.string.playlist_cover),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.ic_music),
                            error = painterResource(id = R.drawable.ic_music),
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.ic_music),
                            contentDescription = stringResource(id = R.string.playlist_cover),
                            colorFilter = ColorFilter.tint(colorResource(id = R.color.primary_text)),
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Transparent),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.track_row_spacing)))

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = stringResource(id = R.string.playlist_name_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        Color.White
                    )
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(text = stringResource(id = R.string.playlist_description_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        Color.White
                    )
                )
            }
            Button(
                onClick = {
                    viewModel.createNewPlaylist(name, description)
                    onBack()
                },
                enabled = name.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.save_playlist))
            }
        }
    }
}
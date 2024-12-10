package com.example.cmsapp.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cmsapp.R

@Composable
fun VideoPicker(onMovieSelect: @Composable (Uri) -> Unit){
    val result = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        result.value = it
    }

    FilledTonalButton(
        onClick = { launcher.launch(
            PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.VideoOnly)
        )},
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(painter = painterResource(R.drawable.movie), contentDescription = "movie")
        Spacer(Modifier.width(8.dp))
        Text("Pick a Video from Device")
    }

    result.value?.let { uri ->
        onMovieSelect(uri)
    }

}
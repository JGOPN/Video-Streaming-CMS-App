package com.example.cmsapp.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cmsapp.R
import com.example.cmsapp.ui.main.AddMovieScreen
import com.example.cmsapp.ui.main.MainScreens
import com.example.cmsapp.ui.theme.CMSappTheme

@Composable
fun VideoPicker(onMovieSelect: @Composable (Uri) -> Unit, onCancel: () -> Unit){
    val result = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        result.value = it
    }

    Row(modifier = Modifier.fillMaxWidth()){

        FilledTonalButton(
            onClick = { launcher.launch(
                PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.VideoOnly)
            )},
            modifier = Modifier.weight(0.8f)
        ) {
            Icon(painter = painterResource(R.drawable.movie), contentDescription = "movie")
            Spacer(Modifier.width(8.dp))
            Text("Pick a Video from Device")
        }

        FilledIconButton(
            onClick = {
                result.value=null
                onCancel()
                      },
            modifier = Modifier.weight(0.2f),
            enabled = result.value!=null
        ) {
            Icon(
                Icons.Filled.Clear,
                contentDescription = "Clear selected",
            )
        }
    }

    result.value?.let { uri ->
        onMovieSelect(uri)
    }
}

@Preview(showBackground = true)
@Composable
fun PickerPreview() {
    CMSappTheme{
        VideoPicker({},{})
    }
}
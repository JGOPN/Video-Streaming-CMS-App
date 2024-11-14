package com.example.cmsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.cmsapp.ui.CMSAppScreen
import com.example.cmsapp.ui.theme.CMSappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            CMSappTheme{
                Surface(modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background)
                {
                    CMSAppScreen()
                }
            }
        }
    }
}


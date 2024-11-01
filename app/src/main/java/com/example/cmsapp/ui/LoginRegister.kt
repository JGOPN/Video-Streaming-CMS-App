package com.example.cmsapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cmsapp.R
import com.example.cmsapp.ui.theme.CMSappTheme

@Composable
fun LoginRegisterBase(onSubmitClick: () -> Unit, onSwitch: () -> Unit, modifier: Modifier,lorr: Boolean = false/*substituir por viewmodel*/){
    Column(modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "logo", contentScale = ContentScale.Fit, modifier = Modifier.size(100.dp).padding(10.dp))
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Username") }
        )
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.height(20.dp))
        if(lorr) //login
            LoginScreen(onSubmitClick = onSubmitClick, onSwitch = onSwitch, modifier = modifier)
        else
            RegisterScreen(onSubmitClick = onSubmitClick, onSwitch = onSwitch, modifier = modifier)
    }
}

@Composable
fun LoginScreen(onSubmitClick: () -> Unit, onSwitch: () -> Unit, modifier: Modifier){
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
            FilledTonalButton(
                onClick = onSwitch,
                modifier = Modifier.weight(1f),
                contentPadding = ButtonDefaults.TextButtonContentPadding
            ) {
                Text("Register User", fontSize = 20.sp)
            }
            Button(
                onClick = onSubmitClick,
                modifier = Modifier.weight(1f),
                contentPadding = ButtonDefaults.TextButtonContentPadding
            ) {
                Text("Log In", fontSize = 20.sp)
            }
        }
}

@Composable
fun RegisterScreen(onSubmitClick: () -> Unit, onSwitch: () -> Unit, modifier: Modifier){
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
            FilledTonalButton(
                onClick = onSwitch,
                modifier = Modifier.weight(1f),
                contentPadding = ButtonDefaults.TextButtonContentPadding
            ) {
                Text("Log In", fontSize = 20.sp)
            }
            Button(
                onClick = onSubmitClick,
                modifier = Modifier.weight(1f),
                contentPadding = ButtonDefaults.TextButtonContentPadding
            ) {
                Text("Register", fontSize = 20.sp)
            }
        }
}

@Preview(showBackground = true)
@Composable
fun LogInPreview() {
    CMSappTheme{
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            //LoginScreen({},{},Modifier.padding(innerPadding))
            RegisterScreen({},{},Modifier.padding(innerPadding))
        }
    }
}
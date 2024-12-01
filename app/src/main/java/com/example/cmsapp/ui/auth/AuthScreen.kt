package com.example.cmsapp.ui.auth

import android.util.Log
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cmsapp.R
import com.example.cmsapp.ui.AuthViewModel
import com.example.cmsapp.ui.components.MinimalDialog
import com.example.cmsapp.ui.theme.CMSappTheme

@Composable
fun AuthBaseScreen(onLogin: () -> Unit, modifier: Modifier, authViewModel : AuthViewModel = viewModel()){
    val authUiState by authViewModel.authUiState.collectAsState()

    //When user clicks submit, may open alertDialog if form not valid, otherwise triggers onSubmit()
    //For some reason this dialog doesnt center text??
    if(authUiState.isDialogOpen){
        val errorList = authViewModel.validateLoginInput()
        if(errorList.isNotEmpty())
            MinimalDialog(
                messageList = errorList,
                onDismissRequest = {authViewModel.toggleConfirmationDialog()}
            )
        else
            onLogin()
    }

    Column(modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.logo), colorFilter = ColorFilter.tint(Color.White),
            contentDescription = "logo", contentScale = ContentScale.Fit, modifier = Modifier
                .size(100.dp)
                .padding(10.dp))
        TextField(
            value = authUiState.username,
            onValueChange = {authViewModel.updateUiState(username = it)},
            label = { Text("Username") },
            singleLine = true
        )
        TextField(
            value = authUiState.password,
            onValueChange = {authViewModel.updateUiState(password = it)},
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(20.dp))

        LoginScreen(onSubmit = {
            authViewModel.toggleConfirmationDialog() },
            modifier = modifier)
    }
}

@Composable
fun LoginScreen(onSubmit: () -> Unit, modifier: Modifier){
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = onSubmit,
                modifier = Modifier.fillMaxWidth(0.5f),
                contentPadding = ButtonDefaults.TextButtonContentPadding
            ) {
                Text("Log In", fontSize = 20.sp)
            }
        }
}



@Preview(showBackground = true)
@Composable
fun LogInPreview() {
    CMSappTheme{
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            LoginScreen({}, Modifier.padding(innerPadding))
        }
    }
}
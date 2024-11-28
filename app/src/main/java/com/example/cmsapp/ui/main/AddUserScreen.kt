package com.example.cmsapp.ui.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cmsapp.ui.components.MinimalDialog
import com.example.cmsapp.ui.theme.CMSappTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.DateTimeArithmeticException


@Composable
fun AddUserScreen(
    userEntryViewModel: UserEntryViewModel = viewModel()
) {
    val userEntryState by userEntryViewModel.userEntryState.collectAsState()
    val userEntry = userEntryState.userEntry
    val birthdateStr = remember { mutableStateOf(userEntry.birthdate.toString()) } //birthdate string so we only parse to LocalDate at submit

    val validateInputs = userEntryViewModel::validateUserEntry
    val onUpdate = userEntryViewModel::updateUserEntryState
    val onSubmit : () -> Unit = {}

    //When user clicks submit, may open alertDialog if form not valid, otherwise triggers onSubmit()
    if(userEntryState.isDialogOpen){
        val errorList = validateInputs()
        if(errorList.isNotEmpty())
            MinimalDialog(
                messageList = errorList,
                onDismissRequest = {userEntryViewModel.toggleConfirmationDialog()}
            )
        else
            onSubmit()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = userEntry.username,
            onValueChange = {  onUpdate(userEntry.copy(username = it)) },
            label = { Text(text="Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = userEntry.email,
            onValueChange = { onUpdate(userEntry.copy(email = it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = userEntry.password,
            onValueChange = { onUpdate(userEntry.copy(password = it)) },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = birthdateStr.value,
            onValueChange = { birthdateStr.value = it },
            label = { Text("Birthdate (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = userEntry.isAdmin,
                onCheckedChange = {onUpdate(userEntry.copy(isAdmin=it)) }
            )
            Text(text = "Is Admin", modifier = Modifier.padding(start = 8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                try {
                    onUpdate(userEntry.copy(birthdate = LocalDate.parse(birthdateStr.value))) //handle parseError on LocalDate
                } catch (ex : Exception) {
                    Log.d("MainActivity","Birthdate Parse error")
                }
                userEntryViewModel.toggleConfirmationDialog()//show dialog if has errors, submit otherwise
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Submit")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddUserPreview() {
    CMSappTheme{
        //AddUserScreen({}, userEntryState = UserEntryState(Datasource.users[0]),{})
    }
}
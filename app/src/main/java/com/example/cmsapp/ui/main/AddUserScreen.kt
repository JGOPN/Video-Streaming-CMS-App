package com.example.cmsapp.ui.main

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cmsapp.data.Datasource
import com.example.cmsapp.model.User
import com.example.cmsapp.ui.theme.CMSappTheme
import java.time.LocalDate


@Composable
fun AddUserScreen(//TODO: make this look nicer
    onSubmit: (User) -> Unit,
    //viewModel : MainViewModel,
    userEntryState: UserEntryState,
    onUpdate : (User) -> Unit
) {
    //val userEntryState by viewModel.userEntryState.collectAsState()
    val userEntry = userEntryState.userEntry

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add a new User", style = MaterialTheme.typography.titleLarge)
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
        TextField(//use datepicker instead?
            value = userEntry.birthdate.toString(),
            onValueChange = { onUpdate(userEntry.copy(birthdate = userEntry.birthdate))},
            label = { Text("Birthdate (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth(),
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
                if (userEntry.username.isNotEmpty() && userEntry.email.isNotEmpty() && userEntry.password.isNotEmpty()) {
                    val user = User(id = 0, username = userEntry.username, email = userEntry.email, password = userEntry.password, isAdmin = userEntry.isAdmin, birthdate = LocalDate.of(1991,1,1))
                    onSubmit(user)
                } else {
                    // Handle validation error
                }
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
        AddUserScreen({}, userEntryState = UserEntryState(Datasource.users[0]),{})
    }
}
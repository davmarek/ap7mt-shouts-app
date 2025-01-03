package cz.davmarek.shouts.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import cz.davmarek.shouts.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController?
) {
    val context  = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Login") }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Login Screen")
            Button(onClick = {
                navController?.navigate("ShoutsScreen")
            }) {
                Text("Go to home")
            }
            Button(onClick = {
                SessionManager(context).saveAuthToken("some_token")
            }) {
                Text("Save token")
            }
        }
    }
}


@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen(navController = null)
}
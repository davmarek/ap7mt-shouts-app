package cz.davmarek.shouts.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.davmarek.shouts.SessionManager
import cz.davmarek.shouts.models.Shout
import cz.davmarek.shouts.viewmodels.ShoutsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoutsScreen(
    navController: NavController?,
    viewModel: ShoutsViewModel
) {
    val viewState = viewModel.viewState.collectAsState()
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val token = SessionManager(context).getAuthToken()
        if(token == null){
            navController?.navigate("LoginScreen") {
                popUpTo("ShoutsScreen") { inclusive = true }
            }
        }

        viewModel.setContext(context)
        viewModel.fetchShouts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Shouts"
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController?.navigate("ShoutCreateScreen")
                }
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
        bottomBar = {
            NavigationBar(
                contentColor = Color.Black
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    },
                    label = { Text("Check") },
                    selected = true,
                    onClick = { /* do something */ }

                )

                NavigationBarItem(
                    icon = {
                        Icon(Icons.Default.Delete, contentDescription = "Logout")
                    },
                    label = { Text("Logout") },
                    selected = false,
                    onClick = {
                        Log.i("ShoutsScreen", "Clearing token");
                        SessionManager(context).clearAuthToken()
                        navController?.navigate("LoginScreen") {
                            popUpTo("ShoutsScreen") { inclusive = true }
                        }
                    }

                )

            }

        }

    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = viewState.value.search,
                    onValueChange = { viewModel.onSearchChanged(it) },
                    label = { Text("Search") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Text
                    ),

                    keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.searchShouts()
                            Log.i("ShoutsScreen", "Search IME button clicked");
                            focusManager.clearFocus()
                        }
                    ),

                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
//                    leadingIcon = {
//                        Icon(Icons.Default.Check, contentDescription = "Check")
//                    }
                )


                IconButton(onClick = {
                    Log.i("ShoutsScreen", "Search button clicked");
                    viewModel.searchShouts()
                    focusManager.clearFocus()

                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(viewState.value.shouts) { shout ->
                    ShoutItem(shout = shout) {
                        navController?.navigate("ShoutDetailScreen/${shout.id}")
                    }

                }
            }
        }
    }
}

@Composable
fun ShoutItem(
    shout: Shout,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp)
        ) {

            Text(
                text = "@${shout.user.username}",
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = shout.text,
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Preview
@Composable
fun ShoutsScreenPreview() {
    ShoutsScreen(navController = null, viewModel = ShoutsViewModel(mock = true))
}
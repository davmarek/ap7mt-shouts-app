package cz.davmarek.shouts.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
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
import cz.davmarek.shouts.ui.components.ShoutItem
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
        if (token == null) {
            navController?.navigate("LoginScreen") {
                popUpTo("ShoutsScreen") { inclusive = true }
            }
        }

        viewModel.setContext(context)
        focusManager.clearFocus()
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
                    label = { Text("Home") },
                    selected = true,
                    onClick = {
                        // TODO: add refresh shouts functionality
                    }

                )

                NavigationBarItem(
                    icon = {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    },
                    label = { Text("Profile") },
                    selected = false,
                    onClick = {
                        SessionManager(context).getUserId().let {
                            navController?.navigate("UserDetailScreen/$it")
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
                            focusManager.clearFocus()
                            viewModel.searchShouts(navController)
                        }
                    ),

                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                )


                IconButton(onClick = {
                    focusManager.clearFocus()
                    viewModel.searchShouts(navController)

                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
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




@Preview
@Composable
fun ShoutsScreenPreview() {
    ShoutsScreen(navController = null, viewModel = ShoutsViewModel(mock = true))
}
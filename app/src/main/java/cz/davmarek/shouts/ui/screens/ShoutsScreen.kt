package cz.davmarek.shouts.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
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
    LaunchedEffect(context) {
        viewModel.setContext(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Color.Yellow,

                    ),
                title = {
                    Text(
                        text = "Shouts"
                    )
                }
            )
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
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    },
                    label = { Text("Edit") },
                    selected = false,
                    onClick = {
                        Log.i("ShoutsScreen", "Clearing token");
                        SessionManager(context).clearAuthToken()
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


                IconButton (onClick = {
                    Log.i("ShoutsScreen", "Search button clicked");
                    viewModel.searchShouts()
                    focusManager.clearFocus()

                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(viewState.value.shoutsList) { shout ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .clickable {
                                navController?.navigate("ShoutDetailScreen/${shout}")
                            }

                    ) {
                        Text(
                            text = shout.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

            }


        }
    }
}


@Preview
@Composable
fun ShoutsScreenPreview() {
    ShoutsScreen(navController = null, viewModel = ShoutsViewModel())
}
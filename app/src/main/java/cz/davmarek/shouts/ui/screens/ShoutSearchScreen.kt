package cz.davmarek.shouts.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.davmarek.shouts.SessionManager
import cz.davmarek.shouts.ui.components.ConfirmDialog
import cz.davmarek.shouts.ui.components.NavigationBackButton
import cz.davmarek.shouts.ui.components.ShoutItem
import cz.davmarek.shouts.viewmodels.ShoutSearchViewModel
import cz.davmarek.shouts.viewmodels.UserDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoutSearchScreen(
    navController: NavController?,
    viewModel: ShoutSearchViewModel,
    searchQuery: String
) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.setContext(context)
        viewModel.fetchShouts(searchQuery)
    }

    val viewState = viewModel.viewState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Search: $searchQuery",
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                },
                navigationIcon = {
                    NavigationBackButton(
                        onClick = {
                            navController?.popBackStack()
                        }
                    )
                },
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {

            if (!viewState.value.isLoading && viewState.value.shouts.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No shouts found", modifier = Modifier.padding(16.dp))
                }
                return@Column
            }

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
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
fun ShoutSearchScreenPreview() {
    ShoutSearchScreen(
        navController = null,
        viewModel = ShoutSearchViewModel(mock = true),
        searchQuery = "placeholder search query super long query like the world is ending",
    )
}
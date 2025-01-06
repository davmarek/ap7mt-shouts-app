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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.davmarek.shouts.SessionManager
import cz.davmarek.shouts.ui.components.ConfirmDialog
import cz.davmarek.shouts.ui.components.NavigationBackButton
import cz.davmarek.shouts.ui.components.ShoutItem
import cz.davmarek.shouts.viewmodels.UserDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    navController: NavController?,
    viewModel: UserDetailViewModel,
    userId: String
) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.setContext(context)
        viewModel.fetchUser(userId)
        viewModel.fetchUserShouts(userId)
    }

    val viewState = viewModel.viewState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile"
                    )
                },
                navigationIcon = {
                    NavigationBackButton(
                        onClick = {
                            navController?.popBackStack()
                        }
                    )
                },

                actions = {

                    if (viewState.value.mineProfile) {

                        IconButton(onClick = {
                            viewModel.openLogoutDialog()
                        }) {
                            Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout")
                        }
                    }
                }

            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {

            when {
                viewState.value.openLogoutDialog ->
                    ConfirmDialog(
                        onDismissRequest = {
                            viewModel.closeLogoutDialog()
                        },
                        onConfirmation = {
                            viewModel.closeLogoutDialog()
                            SessionManager(context).clearAuthToken()
                            navController?.navigate("LoginScreen") {
                                popUpTo("ShoutsScreen") { inclusive = true }
                            }
                        },
                        dialogTitle = "Log out",
                        dialogText = "Are you sure you want to log out?",
                        icon = {
                            Icon(
                                Icons.AutoMirrored.Filled.Logout,
                                contentDescription = "Logout"
                            )
                        }
                    )
            }

            Text(
                text = "@" + (viewState.value.user?.username ?: ""),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = viewState.value.user?.id ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
            )

            LazyColumn(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxSize(),
                contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp),
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
fun UserDetailScreenPreview() {
    UserDetailScreen(
        navController = null,
        viewModel = UserDetailViewModel(mock = true),
        userId = "placeholder preview user id",
    )
}
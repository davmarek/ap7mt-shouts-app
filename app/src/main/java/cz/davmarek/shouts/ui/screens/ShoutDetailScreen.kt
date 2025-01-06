package cz.davmarek.shouts.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import cz.davmarek.shouts.ui.components.ConfirmDialog
import cz.davmarek.shouts.ui.components.NavigationBackButton
import cz.davmarek.shouts.utils.formatDateForUI
import cz.davmarek.shouts.viewmodels.ShoutDetailViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoutDetailScreen(
    navController: NavController?, viewModel: ShoutDetailViewModel, shoutId: String
) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.setContext(context)
        viewModel.fetchShout(shoutId)
    }

    val viewState = viewModel.viewState.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Post"
            )
        }, navigationIcon = {
            NavigationBackButton(onClick = {
                navController?.popBackStack()
            })
        },

            actions = {

                if (viewState.value.isShoutMine) {
                    IconButton(onClick = {
                        navController?.navigate("ShoutEditScreen/${shoutId}")
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = {
                        viewModel.openDeleteDialog()
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }

        )
    }) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {

            when {
                viewState.value.openDeleteDialog -> ConfirmDialog(
                    onDismissRequest = {
                        viewModel.closeDeleteDialog()
                    },
                    onConfirmation = {
                        viewModel.closeDeleteDialog()
                        viewModel.deleteShout()
                        navController?.popBackStack()
                        Log.d("ShoutDetailScreen", "Delete confirmed")
                    },
                )
            }

            Text(
                text = "@" + (viewState.value.shout?.user?.username ?: ""),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.clickable(
                    onClick = {
                        viewState.value.shout?.userId?.let {
                            navController?.navigate("UserDetailScreen/$it")
                        }
                    }
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = formatDateForUI(viewState.value.shout?.createdAt ?: Date()),
                color = MaterialTheme.colorScheme.secondary,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = viewState.value.shout?.text ?: "",
                style = MaterialTheme.typography.bodyLarge,
            )

        }
    }
}


@Preview
@Composable
fun ShoutDetailScreenPreview() {
    ShoutDetailScreen(
        navController = null,
        viewModel = ShoutDetailViewModel(mock = true),
        shoutId = "placeholder preview id",
    )
}
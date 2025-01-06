package cz.davmarek.shouts.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.davmarek.shouts.ui.components.NavigationBackButton
import cz.davmarek.shouts.viewmodels.ShoutCreateViewModel
import cz.davmarek.shouts.viewmodels.ShoutEditViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoutEditScreen(
    navController: NavController?,
    viewModel: ShoutEditViewModel,
    shoutId: String
) {

    val viewState = viewModel.viewState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.setContext(context)
        viewModel.fetchShout(shoutId)
    }

    LaunchedEffect(viewState.value.shouldClose) {
        if (viewState.value.shouldClose) {
            viewModel.setShouldClose(false)
            navController?.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit shout"
                    )
                },
                navigationIcon = {
                    NavigationBackButton(
                        onClick = {
                            navController?.popBackStack()
                        }
                    )
                }

            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp),
        ) {
            TextField(
                value = viewState.value.text,
                enabled = viewState.value.isLoading.not(),
                onValueChange = {
                    viewModel.onTextChanged(it)
                    Log.d("ShoutEditScreen", "onValueChange=$it")
                },
                label = { Text("Make some changes") },
                minLines = 5,
                maxLines = 10,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.onSubmit()
                },
                enabled = viewState.value.isLoading.not(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("(RE)SHOUT!")
            }

        }
    }
}

@Preview
@Composable
fun ShoutEditScreenPreview() {
    ShoutEditScreen(
        navController = null,
        viewModel = ShoutEditViewModel(),
        shoutId = "preview placeholder id"
    )
}
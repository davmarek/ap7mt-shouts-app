package cz.davmarek.shouts.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import cz.davmarek.shouts.viewmodels.ShoutDetailViewModel
import cz.davmarek.shouts.viewmodels.ShoutsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoutCreateScreen(
    navController: NavController?,
    viewModel: ShoutCreateViewModel,
) {

    val viewState = viewModel.viewState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.setContext(context)
        viewModel.clearText()
    }

    LaunchedEffect(viewState.value.shouldClose) {
        if (viewState.value.shouldClose) {
            navController?.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Shout something..."
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
                    Log.d("ShoutCreateScreen", "onValueChange=$it")
                },
                label = { Text("What is on your mind?") },
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
                Text("SHOUT!")
            }

        }
    }
}

@Preview
@Composable
fun ShoutCreateScreenPreview() {
    ShoutCreateScreen(
        navController = null,
        viewModel = ShoutCreateViewModel(),
    )
}
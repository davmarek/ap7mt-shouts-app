package cz.davmarek.shouts.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import cz.davmarek.shouts.viewmodels.ShoutDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoutDetailScreen(
    navController: NavController?,
    viewModel: ShoutDetailViewModel,
    shoutId: String
) {

    LaunchedEffect(shoutId) {
        viewModel.initLoad(shoutId)
    }

    val viewState = viewModel.viewState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Shout Detail"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController?.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back")
                    }
                }

            )
        }
    ){ innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Text(
                text = viewState.value.shoutId
            )
            Text(
                text = shoutId
            )
        }
    }
}

@Preview
@Composable
fun ShoutDetailScreenPreview() {
    ShoutDetailScreen(
        navController = null,
        viewModel = ShoutDetailViewModel(),
        shoutId = "placeholder preview id"
    )
}
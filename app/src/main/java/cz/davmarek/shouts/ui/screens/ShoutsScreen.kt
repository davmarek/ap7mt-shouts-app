package cz.davmarek.shouts.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import cz.davmarek.shouts.viewmodels.ShoutsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoutsScreen(
    navController: NavController?,
    viewModel: ShoutsViewModel
) {
    val viewState = viewModel.viewState.collectAsState()

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
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Text("Shouts Screen")
            Text("Some other text just for a demonstration")
            Text(viewState.value.isLoading.toString())

        }
    }
}

@Preview
@Composable
fun ShoutsScreenPreview() {
    ShoutsScreen(navController = null, viewModel = ShoutsViewModel())
}
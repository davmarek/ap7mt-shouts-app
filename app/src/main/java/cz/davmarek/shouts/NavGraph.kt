package cz.davmarek.shouts

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cz.davmarek.shouts.ui.screens.ShoutsScreen
import cz.davmarek.shouts.viewmodels.ShoutsViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    shoutsViewModel: ShoutsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "ShoutsScreen"
    ){
        composable("ShoutsScreen") {
            ShoutsScreen(
                navController = navController,
                viewModel = shoutsViewModel
            )
        }
    }
}
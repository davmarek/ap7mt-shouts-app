package cz.davmarek.shouts

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cz.davmarek.shouts.ui.screens.LoginScreen
import cz.davmarek.shouts.ui.screens.ShoutDetailScreen
import cz.davmarek.shouts.ui.screens.ShoutsScreen
import cz.davmarek.shouts.viewmodels.ShoutDetailViewModel
import cz.davmarek.shouts.viewmodels.ShoutsViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String,
    shoutsViewModel: ShoutsViewModel,
    shoutDetailViewModel: ShoutDetailViewModel,

    ) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("ShoutsScreen") {
            ShoutsScreen(
                navController = navController,
                viewModel = shoutsViewModel
            )
        }

        composable("ShoutDetailScreen/{shoutId}") { backStackEntry ->
            val shoutId = backStackEntry.arguments?.getString("shoutId") ?: ""
            ShoutDetailScreen(
                navController = navController,
                viewModel = shoutDetailViewModel,
                shoutId = shoutId
            )
        }


        composable("LoginScreen") {
            LoginScreen(
                navController = navController,

                )
        }
    }
}
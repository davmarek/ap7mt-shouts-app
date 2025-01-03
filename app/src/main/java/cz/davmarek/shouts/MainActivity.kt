package cz.davmarek.shouts

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import cz.davmarek.shouts.api.RetrofitInstance
import cz.davmarek.shouts.ui.theme.ShoutsTheme
import cz.davmarek.shouts.viewmodels.ShoutDetailViewModel
import cz.davmarek.shouts.viewmodels.ShoutsViewModel

class MainActivity : ComponentActivity() {

    private lateinit var shoutsViewModel: ShoutsViewModel
    private lateinit var shoutDetailViewModel: ShoutDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        val token = SessionManager(this).fetchAuthToken()
        RetrofitInstance.setTokenProvider {  SessionManager(this).fetchAuthToken() }


        super.onCreate(savedInstanceState)

        shoutsViewModel = ViewModelProvider(this)[ShoutsViewModel::class.java]
        shoutDetailViewModel = ViewModelProvider(this)[ShoutDetailViewModel::class.java]


        val startDestination = if (token.isNullOrEmpty()) "LoginScreen" else "ShoutsScreen"


        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                AppNavGraph(
                    navController,
                    startDestination,
                    shoutsViewModel = shoutsViewModel,
                    shoutDetailViewModel = shoutDetailViewModel
                )
            }
        }
    }
}

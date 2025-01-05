package cz.davmarek.shouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import cz.davmarek.shouts.api.RetrofitInstance
import cz.davmarek.shouts.ui.theme.ShoutsTheme
import cz.davmarek.shouts.viewmodels.LoginViewModel
import cz.davmarek.shouts.viewmodels.ShoutCreateViewModel
import cz.davmarek.shouts.viewmodels.ShoutDetailViewModel
import cz.davmarek.shouts.viewmodels.ShoutsViewModel

class MainActivity : ComponentActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var shoutsViewModel: ShoutsViewModel
    private lateinit var shoutDetailViewModel: ShoutDetailViewModel
    private lateinit var shoutCreateViewModel: ShoutCreateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        val token = SessionManager(this).getAuthToken()
        RetrofitInstance.setTokenProvider {  SessionManager(this).getAuthToken() }


        super.onCreate(savedInstanceState)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        shoutsViewModel = ViewModelProvider(this)[ShoutsViewModel::class.java]
        shoutDetailViewModel = ViewModelProvider(this)[ShoutDetailViewModel::class.java]
        shoutCreateViewModel = ViewModelProvider(this)[ShoutCreateViewModel::class.java]


        val startDestination = if (token.isNullOrEmpty()) "LoginScreen" else "ShoutsScreen"


        enableEdgeToEdge()
        setContent {
            ShoutsTheme {
                val navController = rememberNavController()
                AppNavGraph(
                    navController,
                    startDestination,
                    loginViewModel = loginViewModel,
                    shoutsViewModel = shoutsViewModel,
                    shoutDetailViewModel = shoutDetailViewModel,
                    shoutCreateViewModel = shoutCreateViewModel
                )
            }
        }
    }
}

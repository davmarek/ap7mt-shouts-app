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
import cz.davmarek.shouts.viewmodels.ShoutEditViewModel
import cz.davmarek.shouts.viewmodels.ShoutSearchViewModel
import cz.davmarek.shouts.viewmodels.ShoutsViewModel
import cz.davmarek.shouts.viewmodels.UserDetailViewModel
import cz.davmarek.shouts.viewstates.ShoutSearchViewState

class MainActivity : ComponentActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var shoutsViewModel: ShoutsViewModel
    private lateinit var shoutDetailViewModel: ShoutDetailViewModel
    private lateinit var shoutCreateViewModel: ShoutCreateViewModel
    private lateinit var shoutEditViewModel: ShoutEditViewModel
    private lateinit var userDetailViewModel: UserDetailViewModel
    private lateinit var shoutSearchViewModel: ShoutSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        val token = SessionManager(this).getAuthToken()
        RetrofitInstance.setTokenProvider {  SessionManager(this).getAuthToken() }


        super.onCreate(savedInstanceState)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        shoutsViewModel = ViewModelProvider(this)[ShoutsViewModel::class.java]
        shoutDetailViewModel = ViewModelProvider(this)[ShoutDetailViewModel::class.java]
        shoutCreateViewModel = ViewModelProvider(this)[ShoutCreateViewModel::class.java]
        shoutEditViewModel = ViewModelProvider(this)[ShoutEditViewModel::class.java]
        shoutSearchViewModel = ViewModelProvider(this)[ShoutSearchViewModel::class.java]
        userDetailViewModel = ViewModelProvider(this)[UserDetailViewModel::class.java]


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
                    shoutCreateViewModel = shoutCreateViewModel,
                    shoutEditViewModel = shoutEditViewModel,
                    shoutSearchViewModel = shoutSearchViewModel,
                    userDetailViewModel = userDetailViewModel
                )
            }
        }
    }
}

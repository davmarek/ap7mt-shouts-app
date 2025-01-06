package cz.davmarek.shouts.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.davmarek.shouts.ui.components.PasswordField
import cz.davmarek.shouts.ui.components.UsernameField
import cz.davmarek.shouts.viewmodels.LoginViewModel
import cz.davmarek.shouts.viewmodels.RegisterViewModel

@Composable
fun RegisterScreen(
    navController: NavController?,
    viewModel: RegisterViewModel
) {
    val context = LocalContext.current
    val viewState = viewModel.viewState.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.setContext(context)
    }

    LaunchedEffect(viewState.value.shouldNavigateToMain) {
        if (viewState.value.shouldNavigateToMain) {
            viewModel.setShouldNavigateToMain(false)
            viewModel.clearFields()

            // navigate to shouts screen
            navController?.navigate("ShoutsScreen"){
                popUpTo("LoginScreen") { inclusive = true }
            }
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.displaySmall,
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            UsernameField(
                value = viewState.value.username,
                onChange = { viewModel.onUsernameChanged(it) },
                label = "Username",
                placeholder = "Enter your username",
                enabled = viewState.value.isLoading.not(),
                modifier = Modifier.fillMaxWidth()
            )

            PasswordField(
                value = viewState.value.password,
                onChange = { viewModel.onPasswordChanged(it) },
                onSubmit = {
                    viewModel.onSubmit()
                },
                label = "Password",
                placeholder = "Enter your password",
                enabled = viewState.value.isLoading.not(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Button(
                onClick = {
                    viewModel.onSubmit()
                },
                enabled = viewState.value.isLoading.not(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign up")
            }

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text("OR")

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            FilledTonalButton(
                onClick = {
                    viewModel.clearFields()
                    navController?.popBackStack()
                },
                enabled = viewState.value.isLoading.not(),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Sign in instead")
            }
        }
    }
}


@Composable
@Preview
fun RegisterScreenPreview() {
    RegisterScreen(navController = null, viewModel = RegisterViewModel())
}
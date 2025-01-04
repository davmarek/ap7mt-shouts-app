package cz.davmarek.shouts.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cz.davmarek.shouts.SessionManager
import cz.davmarek.shouts.ui.components.NavigationBackButton
import cz.davmarek.shouts.viewmodels.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController?,
    viewModel: LoginViewModel
) {
    val context = LocalContext.current
    val viewState = viewModel.viewState.collectAsState()
    val navigateToMainState = viewModel.navigateToMain.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setContext(context)
    }

    LaunchedEffect(navigateToMainState.value) {
        Log.d("LoginScreen", "LaunchedEffect(navigateToMainState)")
        Log.d("LoginScreen", "navigateToMainState: ${navigateToMainState.value}")
        if (navigateToMainState.value) {
            Log.d("LoginScreen", "LaunchedEffect: navigate to ShoutsScreen")
            navController?.navigate("ShoutsScreen")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    NavigationBackButton { navController?.popBackStack() }
                },
                title = {
                    Text(
                        text = "Enter your details",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Log in",
                style = MaterialTheme.typography.displaySmall,
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            LoginField(
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
                Text("SIGN IN")
            }
        }
    }
}

@Composable
fun LoginField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Login",
    placeholder: String = "Enter your Login",
    enabled: Boolean = true,
) {

    val focusManager = LocalFocusManager.current
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }

    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        enabled = enabled,
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun PasswordField(
    value: String,
    onChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    placeholder: String = "Enter your Password",
    enabled: Boolean = true,
    ) {

    var isPasswordVisible by remember { mutableStateOf(false) }

    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Lock,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    val trailingIcon = @Composable {
        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            Icon(
                if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }


    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = { onSubmit() }
        ),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        enabled = enabled,
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}


@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen(navController = null, viewModel = LoginViewModel())
}
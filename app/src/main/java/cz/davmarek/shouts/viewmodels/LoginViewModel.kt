package cz.davmarek.shouts.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.davmarek.shouts.SessionManager
import cz.davmarek.shouts.api.RetrofitInstance
import cz.davmarek.shouts.repositories.AuthRepository
import cz.davmarek.shouts.viewstates.LoginViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val authRepository = AuthRepository(RetrofitInstance.authApi)

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState = _viewState.asStateFlow()


    fun onUsernameChanged(username: String) {
        _viewState.update {
            it.copy(username = username)
        }
    }

    fun onPasswordChanged(password: String) {
        _viewState.update {
            it.copy(password = password)
        }
    }

    fun setContext(context: Context) {
        _viewState.update {
            it.copy(context = context)
        }
    }

    private fun setIsLoading(isLoading: Boolean) {
        _viewState.update {
            it.copy(isLoading = isLoading)
        }
    }

    fun setShouldNavigateToMain(shouldNavigateToMain: Boolean) {
        _viewState.update {
            it.copy(shouldNavigateToMain = shouldNavigateToMain)
        }
    }

    fun clearFields() {
        _viewState.update {
            it.copy(username = "", password = "")
        }
    }

    private fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        _viewState.value.context?.let {
            Toast.makeText(it, message, duration).show()
        }

    }

    fun onSubmit() {
        viewModelScope.launch {
            setIsLoading(true)
            try {
                val token = authRepository.login(
                    _viewState.value.username,
                    _viewState.value.password
                )

                setIsLoading(false)
                _viewState.value.context?.let {
                    SessionManager(it).saveAuthToken(token)
                }

                _viewState.update {
                    it.copy(shouldNavigateToMain = true)
                }

            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error logging in $e", e)
                showToast("Login error $e")
            }
            setIsLoading(false)
        }
    }



}
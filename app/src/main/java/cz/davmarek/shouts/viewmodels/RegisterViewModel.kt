package cz.davmarek.shouts.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParser
import cz.davmarek.shouts.SessionManager
import cz.davmarek.shouts.api.RetrofitInstance
import cz.davmarek.shouts.repositories.AuthRepository
import cz.davmarek.shouts.viewstates.LoginViewState
import cz.davmarek.shouts.viewstates.RegisterViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel : ViewModel() {
    private val authRepository = AuthRepository(RetrofitInstance.authApi)

    private val _viewState = MutableStateFlow(RegisterViewState())
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
        if(_viewState.value.username.isEmpty()){
            showToast("Username cannot be empty")
            return
        }


        if(_viewState.value.password.isEmpty()){
            showToast("Password cannot be empty")
            return
        }

        if(_viewState.value.password.length < 8){
            showToast("Password must be at least 8 characters long")
            return
        }

        viewModelScope.launch {
            setIsLoading(true)
            try {
                val token = authRepository.register(
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

            } catch (e: HttpException) {
                if(e.code() == 409){
                    var message = "Username already taken"
                    e.response()?.errorBody()?.string()?.let { errorBody ->
                        JsonParser.parseString(errorBody).asJsonObject.get("message")?.asString?.let {
                            message = it
                        }
                    }
                    showToast(message)
                    Log.e("RegisterViewModel", "Register Error $message", e)
                } else {
                    Log.e("RegisterViewModel", "Register Error $e", e)
                    showToast("Register error: ${e.code()} ${e.message()}")
                }

                Log.e("RegisterViewModel", "Register Error $e", e)
                showToast("Register error: ${e.code()} ${e.message()}")
            } catch (e: Exception) {
                Log.e("RegisterViewModel", "Register Error $e", e)
                showToast("Register error $e")
            }
            setIsLoading(false)
        }
    }


}
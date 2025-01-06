package cz.davmarek.shouts.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.davmarek.shouts.SessionManager
import cz.davmarek.shouts.api.RetrofitInstance
import cz.davmarek.shouts.models.Shout
import cz.davmarek.shouts.models.ShoutUser
import cz.davmarek.shouts.models.User
import cz.davmarek.shouts.repositories.ShoutsRepository
import cz.davmarek.shouts.repositories.UsersRepository
import cz.davmarek.shouts.viewstates.UserDetailViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class UserDetailViewModel(mock: Boolean = false) : ViewModel() {
    // TODO: replace with user repository
    private val usersRepository = UsersRepository(RetrofitInstance.usersApi)

    // TODO: replace with UserDetailViewState
    private val _viewState = MutableStateFlow(UserDetailViewState())
    val viewState: StateFlow<UserDetailViewState> = _viewState.asStateFlow()

    init {
        if (mock) {
            _viewState.update {
                it.copy(
                    user = User(
                        id = "1",
                        username = "davmarek"
                    ),
                    shouts = listOf(
                        Shout(
                            id = "1",
                            text = "Hello world",
                            createdAt = Date(),
                            userId = "1",
                            user = ShoutUser(
                                id = "1",
                                username = "davmarek"
                            )
                        ),
                        Shout(
                            id = "1",
                            text = "Hello world2",
                            createdAt = Date(),
                            userId = "1",
                            user = ShoutUser(
                                id = "1",
                                username = "davmarek"
                            )
                        )
                    ),
                )
            }
        }
    }


    private fun setIsLoading(isLoading: Boolean) {
        _viewState.update {
            it.copy(isLoading = isLoading)
        }
    }

    fun setContext(context: Context) {
        _viewState.update {
            it.copy(context = context)
        }
    }

    private fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        _viewState.value.context?.let {
            Toast.makeText(it, message, duration).show()
        }

    }

    fun fetchUser(userId: String) {

        viewModelScope.launch {
            setIsLoading(true)
            try {
                val user = usersRepository.getUser(userId)
                var mineProfile = false
                _viewState.value.context?.let {
                    SessionManager(it).getUserId()?.let { userId ->
                        mineProfile = user.id == userId
                    }
                }

                _viewState.update {
                    it.copy(user = user, mineProfile = mineProfile)
                }


            } catch (e: Exception) {
                Log.e("UserDetailViewModel", "Fetching error $e", e)
                showToast("Fetching error $e")
            }
            setIsLoading(false)
        }
    }


    fun openLogoutDialog() {
        _viewState.update {
            it.copy(openLogoutDialog = true)
        }
    }

    fun closeLogoutDialog() {
        _viewState.update {
            it.copy(openLogoutDialog = false)
        }
    }


}
package cz.davmarek.shouts.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.davmarek.shouts.api.RetrofitInstance
import cz.davmarek.shouts.models.Shout
import cz.davmarek.shouts.models.ShoutUser
import cz.davmarek.shouts.repositories.ShoutsRepository
import cz.davmarek.shouts.viewstates.ShoutDetailViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class ShoutDetailViewModel(mock: Boolean = false) : ViewModel() {
    private val shoutsRepository = ShoutsRepository(RetrofitInstance.shoutsApi)

    private val _viewState = MutableStateFlow(ShoutDetailViewState(false, ""))
    val viewState: StateFlow<ShoutDetailViewState> = _viewState.asStateFlow()

    init {
        if (mock) {
            _viewState.update {
                it.copy(
                    shout = Shout(
                        id = "1",
                        text = "Hola mundo para preview",
                        userId = "2",
                        createdAt = Date(),
                        user = ShoutUser("2", "xxxdestroyer")
                    )
                )
            }
        }
    }

    private fun setShoutId(shoutId: String) {
        _viewState.update {
            it.copy(shoutId = shoutId)
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

    fun fetchShout(shoutId: String) {
        setShoutId(shoutId)
        viewModelScope.launch {
            setIsLoading(true)
            try {
                val shout = shoutsRepository.getShout(shoutId)
                _viewState.update {
                    it.copy(shout = shout)
                }

            } catch (e: Exception) {
                Log.e("ShoutDetailViewModel", "Error fetching shouts ${e}", e)
                showToast("Fetching error $e")
            }
            setIsLoading(false)
        }
    }


}
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
import cz.davmarek.shouts.viewstates.ShoutCreateViewState
import cz.davmarek.shouts.viewstates.ShoutsViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShoutCreateViewModel() : ViewModel() {
    private val shoutsRepository = ShoutsRepository(RetrofitInstance.shoutsApi)

    private val _viewState = MutableStateFlow(ShoutCreateViewState())
    val viewState: StateFlow<ShoutCreateViewState> = _viewState.asStateFlow()


    fun onTextChanged(text: String) {
        _viewState.update {
            it.copy(text = text)
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

    private fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        _viewState.value.context?.let {
            Toast.makeText(it, message, duration).show()
        }
    }

    fun clearText() {
        _viewState.update {
            it.copy(text = "")
        }
    }

    fun onSubmit() {

        val newText = viewState.value.text.trim()

        if (newText.isEmpty()) {
            showToast("Text cannot be empty")
            return
        }

        viewModelScope.launch {
            setIsLoading(true)
            try {
                shoutsRepository.createShout(newText)
                _viewState.update {
                    it.copy(shouldClose = true)
                }
            } catch (e: Exception) {
                Log.e("ShoutsViewModel", "Error creating shout ${e.toString()}", e)
                showToast("Creating error $e")
            }
            setIsLoading(false)
        }
    }
}
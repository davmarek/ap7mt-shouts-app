package cz.davmarek.shouts.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.davmarek.shouts.api.RetrofitInstance
import cz.davmarek.shouts.repositories.ShoutsRepository
import cz.davmarek.shouts.viewstates.ShoutEditViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShoutEditViewModel() : ViewModel() {
    private val shoutsRepository = ShoutsRepository(RetrofitInstance.shoutsApi)

    private val _viewState = MutableStateFlow(ShoutEditViewState())
    val viewState: StateFlow<ShoutEditViewState> = _viewState.asStateFlow()


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

    fun setShouldClose(shouldClose: Boolean) {
        _viewState.update {
            it.copy(shouldClose = shouldClose)
        }
    }

    private fun setIsLoading(isLoading: Boolean) {
        _viewState.update {
            it.copy(isLoading = isLoading)
        }
    }


    fun fetchShout(shoutId: String) {
        viewModelScope.launch {
            setIsLoading(true)
            try {

                val shout = shoutsRepository.getShout(shoutId)
                _viewState.update {
                    it.copy(shout = shout, text = shout.text)
                }

            } catch (e: Exception) {
                Log.e("ShoutEditViewModel", "Error fetching shout ${e}", e)
                showToast("Fetching error $e")
            }
            setIsLoading(false)
        }
    }




    fun onSubmit() {
        val newText = viewState.value.text.trim()

        // check if text is empty
        if (newText.isEmpty()) {
            showToast("Text cannot be empty")
            return
        }

        // check if text is the same as the original
        if(newText == viewState.value.shout?.text?.trim() ){
            setShouldClose(true)
            return
        }

        // shout id null check just in case
        val shoutId = viewState.value.shout?.id
        if (shoutId == null) {
            setShouldClose(true)
            return
        }

        // update shout
        viewModelScope.launch {
            setIsLoading(true)
            try {
                shoutsRepository.updateShout(shoutId, newText)
                setShouldClose(true)

            } catch (e: Exception) {
                Log.e("ShoutEditViewModel", "Error updating shout ${e}", e)
                showToast("Updating error $e")
            }
            setIsLoading(false)
        }
    }

    private fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        _viewState.value.context?.let {
            Toast.makeText(it, message, duration).show()
        }
    }
}
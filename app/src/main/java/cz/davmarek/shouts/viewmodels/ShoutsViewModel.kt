package cz.davmarek.shouts.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.davmarek.shouts.api.RetrofitInstance
import cz.davmarek.shouts.api.ShoutsApi
import cz.davmarek.shouts.repositories.ShoutsRepository
import cz.davmarek.shouts.viewstates.ShoutsViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShoutsViewModel : ViewModel() {
    private val booksRepository = ShoutsRepository(RetrofitInstance.shoutsApi)

    private val _viewState = MutableStateFlow(ShoutsViewState())
    val viewState: StateFlow<ShoutsViewState> = _viewState.asStateFlow()

    init {
        // fetchShoutsMock()
        fetchShouts()
    }

    fun setContext(context: Context) {
        _viewState.update {
            it.copy(context = context)
        }
    }

    private fun fetchShouts() {
        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true) }
            try {
                val shouts = booksRepository.getShouts()
                _viewState.update {
                    it.copy(shoutsList = shouts)
                }
                _viewState.value.context?.let {
                    Toast.makeText(it, "Shouts fetched", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("ShoutsViewModel", "Error fetching shouts ${e.toString()}", e)
                _viewState.value.context?.let {
                    Toast.makeText(it, "Fetching error $e", Toast.LENGTH_SHORT).show()
                }
            }
            _viewState.update { it.copy(isLoading = false) }


        }

    }


    fun searchShouts() {
        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true) }
            try {
                val shouts = booksRepository.getShouts()
                _viewState.update {
                    it.copy(shoutsList = shouts)
                }
                _viewState.value.context?.let {
                    Toast.makeText(it, "Shouts fetched", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("ShoutsViewModel", "Error fetching shouts", e)
                _viewState.value.context?.let {
                    Toast.makeText(it, "Fetching error $e", Toast.LENGTH_SHORT).show()
                }
            }
            _viewState.update { it.copy(isLoading = false) }


        }
    }

    fun onSearchChanged(search: String) {
        _viewState.update {
            it.copy(search = search)
        }
    }

    private fun fetchShoutsMock() {
        val list = listOf(
            "Shout 1",
            "Shout 2",
            "Shout 3",
            "Shout 4",
            "Shout 5",
            "Shout 6",
            "Shout 7",
            "Shout 8",
            "Shout 9",
            "Shout 10",
            "Shout 11",
            "Shout 12",
            "Shout 13",
            "Shout 14",
            "Shout 15",
            "Shout 16",
            "Shout 17",
            "Shout 18",
            "Shout 19",
            "Shout 20",
            "Shout 21",
            "Shout 22",
            "Shout 23",
            "Shout 24",
            "Shout 25",
            "Shout 26",
            "Shout 27",
            "Shout 28",
            "Shout 29",
            "Shout 30",
            "Shout 31",
            "Shout 32",
            "Shout 33",
            "Shout 34",
            "Shout 35",
            "Shout 36",
            "Shout 37",
            "Shout 38",
            "Shout 39",
            "Shout 40",
            "Shout 41",
            "Shout 42",
            "Shout 43",
            "Shout 44",
            "Shout 45",
            "Shout 46",
            "Shout 47",
            "Shout 48",
            "Shout 49",
            "Shout 50"
        )
//        _viewState.update {
//            it.copy(shoutsList = list)
//        }
    }
}
package cz.davmarek.shouts.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import cz.davmarek.shouts.api.RetrofitInstance
import cz.davmarek.shouts.models.Shout
import cz.davmarek.shouts.models.ShoutUser
import cz.davmarek.shouts.repositories.ShoutsRepository
import cz.davmarek.shouts.viewstates.ShoutsViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class ShoutsViewModel(mock: Boolean = false) : ViewModel() {
    private val shoutsRepository = ShoutsRepository(RetrofitInstance.shoutsApi)

    private val _viewState = MutableStateFlow(ShoutsViewState())
    val viewState: StateFlow<ShoutsViewState> = _viewState.asStateFlow()

    init {
        if (mock) {
            fetchShoutsMock()
        }
    }

    fun onSearchChanged(search: String) {
        _viewState.update {
            it.copy(search = search)
        }
    }

    fun clearSearch() {
        _viewState.update {
            it.copy(search = "")
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

    private fun setShouts(shouts: List<Shout>) {
        _viewState.update {
            it.copy(shouts = shouts)
        }
    }

    fun fetchShouts() {
        viewModelScope.launch {
            setIsLoading(true)
            try {
                val shouts = shoutsRepository.getShouts()
                setShouts(shouts)
            } catch (e: Exception) {
                Log.e("ShoutsViewModel", "Error fetching shouts ${e}", e)
                showToast("Fetching error $e")
            }
            setIsLoading(false)
        }

    }

    private fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        _viewState.value.context?.let {
            Toast.makeText(it, message, duration).show()
        }

    }


    fun searchShouts(navController: NavController?) {
        if(_viewState.value.search.isEmpty()){
            return
        }

        navController?.navigate("ShoutSearchScreen/${_viewState.value.search}")

        /*viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true) }
            try {
                val shouts = shoutsRepository.getShouts()
                _viewState.update {
                    it.copy(shouts = shouts)
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


        }*/
    }


    private fun fetchShoutsMock() {
        val list = listOf(
            Shout("1", "Hello", "1", ShoutUser("1", "User1"), Date()),
            Shout("2", "Hi", "2", ShoutUser("2", "User2"), Date()),
            Shout("3", "Hey", "3", ShoutUser("3", "User3"), Date()),
            Shout("4", "Hola", "4", ShoutUser("4", "User4"), Date()),
            Shout("5", "Bonjour", "5", ShoutUser("5", "User5"), Date()),
            Shout("6", "Ciao", "6", ShoutUser("6", "User6"), Date()),
            Shout("7", "Hallo", "7", ShoutUser("7", "User7"), Date()),
            Shout("8", "Salut", "8", ShoutUser("8", "User8"), Date()),
            Shout("9", "Privet", "9", ShoutUser("9", "User9"), Date()),
            Shout("10", "Namaste", "10", ShoutUser("10", "User10"), Date()),
            Shout("11", "Konnichiwa", "11", ShoutUser("11", "User11"), Date()),
            Shout("12", "Annyeong", "12", ShoutUser("12", "User12"), Date()),
            Shout("13", "Nihao", "13", ShoutUser("13", "User13"), Date()),
            Shout("14", "Salam", "14", ShoutUser("14", "User14"), Date()),
            Shout("15", "Merhaba", "15", ShoutUser("15", "User15"), Date()),
            Shout("16", "Shalom", "16", ShoutUser("16", "User16"), Date()),
            Shout("17", "Szia", "17", ShoutUser("17", "User17"), Date()),
            Shout("18", "Hej", "18", ShoutUser("18", "User18"), Date()),
            Shout("19", "Hei", "19", ShoutUser("19", "User19"), Date()),
            Shout("20", "Ahoj", "20", ShoutUser("20", "User20"), Date()),
        )

        _viewState.update {
            it.copy(shouts = list)
        }
    }
}
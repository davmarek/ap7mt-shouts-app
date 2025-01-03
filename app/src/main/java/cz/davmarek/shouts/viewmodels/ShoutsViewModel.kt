package cz.davmarek.shouts.viewmodels

import androidx.lifecycle.ViewModel
import cz.davmarek.shouts.viewstates.ShoutsViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShoutsViewModel : ViewModel(){
    private val _viewState = MutableStateFlow(ShoutsViewState())
    val viewState: StateFlow<ShoutsViewState> = _viewState.asStateFlow()
}
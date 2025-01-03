package cz.davmarek.shouts.viewmodels

import androidx.lifecycle.ViewModel
import cz.davmarek.shouts.viewstates.ShoutDetailViewState
import cz.davmarek.shouts.viewstates.ShoutsViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ShoutDetailViewModel : ViewModel() {
    // TODO: pass a real value of shoutId
    private val _viewState = MutableStateFlow(ShoutDetailViewState(false, "placeholder id"))
    val viewState: StateFlow<ShoutDetailViewState> = _viewState.asStateFlow()

    private fun setShoutId(shoutId: String) {
        _viewState.update {
            it.copy(shoutId = shoutId)
        }
    }

    fun initLoad(bookId: String){
        setShoutId(bookId)
        // TODO: implement loading of shout detail from API
    }


}
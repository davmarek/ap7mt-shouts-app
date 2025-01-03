package cz.davmarek.shouts.viewstates

data class ShoutsViewState(
    val isLoading: Boolean = false,
    val shoutsList: List<String> = emptyList()
)
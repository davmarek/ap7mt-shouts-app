package cz.davmarek.shouts.viewstates

data class ShoutDetailViewState(
    val isLoading: Boolean = false,
    val shoutId: String,
)

package cz.davmarek.shouts.viewstates

import cz.davmarek.shouts.models.Shout

data class ShoutsViewState(
    val isLoading: Boolean = false,
    val shoutsList: List<Shout> = emptyList()
)

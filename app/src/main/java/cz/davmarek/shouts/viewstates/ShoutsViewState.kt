package cz.davmarek.shouts.viewstates

import android.content.Context
import cz.davmarek.shouts.models.Shout

data class ShoutsViewState(
    val isLoading: Boolean = false,
    val shoutsList: List<Shout> = emptyList(),
    val search: String = "",

    val context: Context? = null,

    )

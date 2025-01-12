package cz.davmarek.shouts.viewstates

import android.content.Context
import cz.davmarek.shouts.models.Shout

data class ShoutsViewState(
    val isLoading: Boolean = false,
    val shouts: List<Shout> = emptyList(),
    val search: String = "",

    val context: Context? = null,

    )

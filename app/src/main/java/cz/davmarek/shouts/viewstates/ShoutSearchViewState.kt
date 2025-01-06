package cz.davmarek.shouts.viewstates

import android.content.Context
import cz.davmarek.shouts.models.Shout
import cz.davmarek.shouts.models.User

data class ShoutSearchViewState(
    // state variables
    val isLoading: Boolean = false,

    // model related
    val shouts: List<Shout> = emptyList(),

    // context
    val context: Context? = null
)

package cz.davmarek.shouts.viewstates

import android.content.Context
import cz.davmarek.shouts.models.Shout

data class ShoutDetailViewState(
    val isLoading: Boolean = false,
    val shoutId: String = "",
    val shout: Shout? = null,

    val context: Context? = null
)

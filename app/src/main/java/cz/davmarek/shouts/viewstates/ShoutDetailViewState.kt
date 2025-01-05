package cz.davmarek.shouts.viewstates

import android.content.Context
import cz.davmarek.shouts.models.Shout

data class ShoutDetailViewState(
    val isLoading: Boolean = false,
    val shoutId: String = "",
    val shout: Shout? = null,

    val isShoutMine: Boolean = false,

    val openDeleteDialog: Boolean = false,

    val context: Context? = null
)

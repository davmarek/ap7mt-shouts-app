package cz.davmarek.shouts.viewstates

import android.content.Context
import cz.davmarek.shouts.models.Shout

data class ShoutDetailViewState(
    // state variables
    val isLoading: Boolean = false,
    val openDeleteDialog: Boolean = false,

    // model related
    val shout: Shout? = null,
    val isShoutMine: Boolean = false,

    // context
    val context: Context? = null
)

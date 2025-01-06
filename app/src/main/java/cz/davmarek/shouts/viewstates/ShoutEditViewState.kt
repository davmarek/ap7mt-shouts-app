package cz.davmarek.shouts.viewstates

import android.content.Context
import cz.davmarek.shouts.models.Shout

data class ShoutEditViewState(
    // state variables
    val isLoading: Boolean = false,
    val shouldClose: Boolean = false,

    // model related
    val shout: Shout? = null,

    // form
    val text: String = "",

    // context
    val context: Context? = null,
    )

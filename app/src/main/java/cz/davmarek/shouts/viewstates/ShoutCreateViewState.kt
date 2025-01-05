package cz.davmarek.shouts.viewstates

import android.content.Context
import cz.davmarek.shouts.models.Shout

data class ShoutCreateViewState(
    val isLoading: Boolean = false,
    val shouldClose: Boolean = false,

    val text: String = "",

    val context: Context? = null,

    )

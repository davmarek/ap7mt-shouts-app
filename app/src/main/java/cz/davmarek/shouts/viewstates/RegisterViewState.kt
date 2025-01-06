package cz.davmarek.shouts.viewstates

import android.content.Context
import cz.davmarek.shouts.models.Shout

data class RegisterViewState(
    val isLoading: Boolean = false,
    val shouldNavigateToMain: Boolean = false,

    val username: String = "",
    val password: String = "",

    val context: Context? = null

    )

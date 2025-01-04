package cz.davmarek.shouts.viewstates

import android.content.Context
import cz.davmarek.shouts.models.Shout

data class LoginViewState(
    val isLoading: Boolean = false,

    val username: String = "",
    val password: String = "",

    val context: Context? = null

    )

package cz.davmarek.shouts.viewstates

import android.content.Context
import cz.davmarek.shouts.models.Shout
import cz.davmarek.shouts.models.User

data class UserDetailViewState(
    // state variables
    val isLoading: Boolean = false,
    val openLogoutDialog: Boolean = false,

    // model related
    val user: User? = null,
    val shouts: List<Shout> = emptyList(),
    val mineProfile: Boolean = false,

    // context
    val context: Context? = null
)

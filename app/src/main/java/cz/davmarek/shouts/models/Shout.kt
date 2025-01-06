package cz.davmarek.shouts.models

import java.util.Date

// getShouts
data class Shout(
    val id: String,
    val text: String,
    val userId: String,
    val user: ShoutUser?,
    val createdAt: Date,
)

data class ShoutUser(
    val id:String,
    val username: String
)

// createShout
data class CreateShoutRequest(
    val text: String
)
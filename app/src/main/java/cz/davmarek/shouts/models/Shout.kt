package cz.davmarek.shouts.models

data class Shout(
    val id: String,
    val text: String,
    val userId: String,
    val user: ShoutUser
)

data class ShoutUser(
    val id:String,
    val username: String
)

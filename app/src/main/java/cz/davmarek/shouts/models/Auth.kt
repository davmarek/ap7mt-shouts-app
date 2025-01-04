package cz.davmarek.shouts.models

data class AuthRequest(
    val username: String,
    val password: String
)

data class AuthResponse(
    val token: String
)
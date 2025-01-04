package cz.davmarek.shouts.repositories

import cz.davmarek.shouts.api.AuthApi
import cz.davmarek.shouts.models.AuthRequest

class AuthRepository(private val api: AuthApi) {
    suspend fun login(username: String, password: String): String {
        return api.login(AuthRequest(username, password)).token
    }

    suspend fun register(username: String, password: String): String {
        return api.register(AuthRequest(username, password)).token
    }
}
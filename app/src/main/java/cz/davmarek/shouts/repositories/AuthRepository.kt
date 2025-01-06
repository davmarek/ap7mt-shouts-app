package cz.davmarek.shouts.repositories

import cz.davmarek.shouts.api.AuthApi
import cz.davmarek.shouts.models.AuthRequest
import retrofit2.HttpException


class AuthRepository(private val api: AuthApi) {
    @Throws(HttpException::class)
    suspend fun login(username: String, password: String): String {
        return api.login(AuthRequest(username, password)).token
    }

    @Throws(HttpException::class)
    suspend fun register(username: String, password: String): String {
        return api.register(AuthRequest(username, password)).token
    }
}
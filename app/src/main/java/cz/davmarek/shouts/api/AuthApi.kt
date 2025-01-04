package cz.davmarek.shouts.api

import cz.davmarek.shouts.models.AuthRequest
import cz.davmarek.shouts.models.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun login(
        @Body request: AuthRequest
    ): AuthResponse

    @POST("register")
    suspend fun register(
        @Body request: AuthRequest
    ): AuthResponse
}


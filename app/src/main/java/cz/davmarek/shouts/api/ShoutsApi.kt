package cz.davmarek.shouts.api

import cz.davmarek.shouts.models.CreateShoutRequest
import cz.davmarek.shouts.models.Shout
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ShoutsApi {
    @GET("shouts")
    suspend fun getShouts(): List<Shout>

    @GET("shouts/{id}")
    suspend fun getShout(@Path(value = "id", encoded = true) id: String): Shout


    @POST("shouts")
    suspend fun createShout(
        @Body request: CreateShoutRequest
    ): Shout
}
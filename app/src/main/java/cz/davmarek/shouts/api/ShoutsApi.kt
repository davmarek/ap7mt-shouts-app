package cz.davmarek.shouts.api

import cz.davmarek.shouts.models.CreateShoutRequest
import cz.davmarek.shouts.models.Shout
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ShoutsApi {
    @GET("shouts")
    suspend fun getShouts(): List<Shout>


    @POST("shouts")
    suspend fun createShout(
        @Body request: CreateShoutRequest
    ): Shout
}
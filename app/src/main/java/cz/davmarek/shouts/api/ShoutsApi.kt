package cz.davmarek.shouts.api

import cz.davmarek.shouts.models.CreateShoutRequest
import cz.davmarek.shouts.models.Shout
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ShoutsApi {
    @GET("shouts")
    suspend fun getShouts(@Query(value = "limit", encoded = true) id: Int = 50): List<Shout>

    @GET("shouts/{id}")
    suspend fun getShout(@Path(value = "id", encoded = true) id: String): Shout

    @GET("shouts/search")
    suspend fun searchShouts(@Query(value = "query", encoded = true) query: String): List<Shout>


    @POST("shouts")
    suspend fun createShout(
        @Body request: CreateShoutRequest
    ): Shout

    @PATCH("shouts/{id}")
    suspend fun updateShout(
        @Path(value = "id", encoded = true) id: String,
        @Query("text", encoded = true) text: String
    )

    @DELETE("shouts/{id}")
    suspend fun deleteShout(@Path(value = "id", encoded = true) id: String)
}
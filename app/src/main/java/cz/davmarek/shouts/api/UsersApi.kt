package cz.davmarek.shouts.api

import cz.davmarek.shouts.models.Shout
import cz.davmarek.shouts.models.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersApi {


    @GET("users/{id}")
    suspend fun getUser(@Path(value = "id", encoded = true) id: String): User

    @GET("users/{id}/shouts")
    suspend fun getUserShouts(@Path(value = "id", encoded = true) id: String): List<Shout>


//    @POST("shouts")
//    suspend fun createShout(
//        @Body request: CreateShoutRequest
//    ): Shout
//
//    @PATCH("shouts/{id}")
//    suspend fun updateShout(
//        @Path(value = "id", encoded = true) id: String,
//        @Query("text", encoded = true) text: String
//    )
//
//    @DELETE("shouts/{id}")
//    suspend fun deleteShout(@Path(value = "id", encoded = true) id: String)
}
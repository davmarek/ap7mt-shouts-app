package cz.davmarek.shouts.api

import cz.davmarek.shouts.models.Shout
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ShoutsApi {
    @GET("shouts")
    suspend fun getShouts(): List<Shout>

    // https://api.sampleapis.com/coffee/hot/search?query=espresso
    @GET("coffee/hot/search")
    suspend fun searchShouts(
        @Query("query") query: String
    ): List<Shout>


    @POST("coffee/hot/create")
    suspend fun createBook(
        @Body query: String
    ): Shout
}
package cz.davmarek.shouts.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitInstance {

    private var tokenProvider: () -> String? = { null }

    fun setTokenProvider(tokenProvider: () -> String?) {
        this.tokenProvider = tokenProvider
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenProvider))
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val shoutsApi: ShoutsApi by lazy {
        retrofit.create(ShoutsApi::class.java)
    }
}
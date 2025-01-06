package cz.davmarek.shouts.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitInstance {

    private var tokenProvider: () -> String? = { null }

    fun setTokenProvider(tokenProvider: () -> String?) {
        this.tokenProvider = tokenProvider
    }

    private val gson by lazy {
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
            .create()
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
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val shoutsApi: ShoutsApi by lazy {
        retrofit.create(ShoutsApi::class.java)
    }

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    val usersApi: UsersApi by lazy {
        retrofit.create(UsersApi::class.java)
    }
}
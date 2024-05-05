package com.example.mute

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private val okHttpclient = OkHttpClient.Builder().connectTimeout(10, TimeUnit.MINUTES)
        .readTimeout(6000, TimeUnit.SECONDS).writeTimeout(6000, TimeUnit.SECONDS).build()

    private val client = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpclient)
        .addConverterFactory(GsonConverterFactory.create()).build()

    fun getInstance(): Retrofit = client
}
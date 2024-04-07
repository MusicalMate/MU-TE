package com.example.mute

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/api/auth/login/kakao")
    suspend fun postSignInKakao(@Body signInRequest: SignInRequest): LoginResponse
}
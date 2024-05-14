package com.example.mute.model

import com.example.mute.model.dto.LoginResponse
import com.example.mute.model.dto.SignInRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/api/auth/login/kakao")
    suspend fun postSignInKakao(@Body signInRequest: SignInRequest): LoginResponse
}
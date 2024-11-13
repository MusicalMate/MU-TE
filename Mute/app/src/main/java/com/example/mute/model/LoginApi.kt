package com.example.mute.model

import com.example.mute.model.dto.LoginResponse
import com.example.mute.model.dto.SignInRequest
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Url

interface LoginApi {

    @POST("/api/auth/login/kakao")
    suspend fun postSignInKakao(@Body signInRequest: SignInRequest): LoginResponse
}
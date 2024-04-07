package com.example.mute

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("accessToken") val accessToken: String
)

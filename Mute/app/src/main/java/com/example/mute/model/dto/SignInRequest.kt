package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("accessToken") val accessToken: String
)

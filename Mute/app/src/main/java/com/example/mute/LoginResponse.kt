package com.example.mute

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("userId") val userId: Long
)

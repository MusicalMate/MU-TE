package com.example.mute

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user_id") val userId: Long = 111,
    @SerializedName("email") val email: String = "1111",
    @SerializedName("nickname") val nickname: String = "1111"
)

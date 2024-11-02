package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("userId") val userId: Long?,
    @SerializedName("email") val email: String?,
    @SerializedName("nickname") val nickname: String?
)

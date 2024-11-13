package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class PostVideoUploadResponse(
    @SerializedName("success") val success: String,
    @SerializedName("videoId") val videoId: String
)
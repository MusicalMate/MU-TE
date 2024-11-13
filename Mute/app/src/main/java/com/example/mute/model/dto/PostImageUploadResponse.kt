package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class PostImageUploadResponse(
    @SerializedName("success") val success: String,
    @SerializedName("imageId") val imageId: String
)

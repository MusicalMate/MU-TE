package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class PostImageResponse(
    @SerializedName("s3url") val s3url: String,
    @SerializedName("imageId") val imageId: String,
    @SerializedName("imageKey") val imageKey: String
)
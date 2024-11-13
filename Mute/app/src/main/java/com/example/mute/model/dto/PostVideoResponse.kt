package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class PostVideoResponse(
    @SerializedName("s3url") val s3url: String,
    @SerializedName("videoId") val videoId: String,
    @SerializedName("videoKey") val videoKey: String
)

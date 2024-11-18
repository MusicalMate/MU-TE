package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class StreamingVideoResponse(
    @SerializedName("videoTitle") val videoTitle: String,
    @SerializedName("musicalTitle") val musicalTitle: String,
    @SerializedName("description") val description: String,
    @SerializedName("videoTime") val videoTime: String,
    @SerializedName("presignedUrls") val presignedUrls: List<String>,
    @SerializedName("actors") val actors: List<String>
)

package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class StreamingImageResponse(
    @SerializedName("imageTitle") val imageTitle: String,
    @SerializedName("musicalTitle") val musicalTitle: String,
    @SerializedName("description") val description: String,
    @SerializedName("imageTime") val imageTime: String,
    @SerializedName("presignedUrl") val presignedUrl: List<String>,
    @SerializedName("actors") val actors: List<String>
)
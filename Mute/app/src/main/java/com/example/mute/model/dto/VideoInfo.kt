package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class VideoInfo(
    @SerializedName("videoCoverKeys") val videoCoverKeys: String,
    @SerializedName("videoId") val videoId: String,
    @SerializedName("videoTitle") val videoTitle: String
)
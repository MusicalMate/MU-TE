package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class ActorPlayListResponse(
    @SerializedName("actorDescribe") val actorDescribe: String,
    @SerializedName("actorFilmo") val actorFilmo: String,
    @SerializedName("videoInfo") val videoInfo: VideoInfo,
    @SerializedName("imageInfo") val imageInfo: ImageInfo
)
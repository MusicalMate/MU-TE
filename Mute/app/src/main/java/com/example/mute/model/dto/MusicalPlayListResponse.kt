package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class MusicalPlayListResponse(
    @SerializedName("musicalDescribe") val musicalDescribe: String,
    @SerializedName("musicalTime") val musicalTime: String,
    @SerializedName("videoInfo") val videoInfo: VideoInfo,
    @SerializedName("imageInfo") val imageInfo: ImageInfo
)
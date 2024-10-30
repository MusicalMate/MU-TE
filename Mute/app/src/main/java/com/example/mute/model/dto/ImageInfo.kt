package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class ImageInfo(
    @SerializedName("imageCoverKeys") val imageCoverKeys: String,
    @SerializedName("imageId") val imageId: String,
    @SerializedName("imageTitle") val imageTitle: String
)

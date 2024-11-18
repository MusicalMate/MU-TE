package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class FavoriteStatusResponse(
    @SerializedName("star") val star: String
)

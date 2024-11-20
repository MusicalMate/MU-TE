package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class FavoriteStateResponse(
    @SerializedName("star") val star: String
)

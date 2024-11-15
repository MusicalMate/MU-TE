package com.example.mute.model

import com.google.gson.annotations.SerializedName

data class Actor(
    @SerializedName("actorId") val actorId: String,
    @SerializedName("actorName") val actorName: String
)
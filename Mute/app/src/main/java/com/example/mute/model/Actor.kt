package com.example.mute.model

import com.google.gson.annotations.SerializedName

data class Actor(
    @SerializedName("actorId") val actorId: String = "-1",
    @SerializedName("actorUrl") val actorUrl: String = "",
    @SerializedName("actorName") val actorName: String = ""
)
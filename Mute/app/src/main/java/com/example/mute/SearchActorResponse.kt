package com.example.mute

import com.google.gson.annotations.SerializedName

data class SearchActorResponse(
    @SerializedName("actorId") val actorId: String,
    @SerializedName("actorDescription") val actorDescription: String,
    @SerializedName("actorName") val actorName: String,
    @SerializedName("filmography") val filmography: String
)
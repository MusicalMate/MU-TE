package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class SearchActorResponse(
    @SerializedName("actorId") val actorId: Long,
    @SerializedName("star") val star: String,
    @SerializedName("actorDescription") val actorDescription: String,
    @SerializedName("actorName") val actorName: String,
    @SerializedName("filmography") val filmo: String,
    @SerializedName("image") val actorImg: String
)
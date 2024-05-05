package com.example.mute

import com.google.gson.annotations.SerializedName

data class SearchMusicalResponse(
    @SerializedName("musicalId") val musicalId: Long,
    @SerializedName("musicalTitle") val musicalTitle: String,
    @SerializedName("musicalTime") val musicalTime: String,
    @SerializedName("musicalDescription") val musicalDescription: String,
    @SerializedName("actorInMusical") val actors: String,
    @SerializedName("image") val musicalImg: String
)
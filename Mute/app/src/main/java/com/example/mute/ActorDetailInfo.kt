package com.example.mute

import com.google.gson.annotations.SerializedName

data class ActorDetailInfo(
    val actorId: Long = 0,
    val actorDescription: String = "",
    val actorName: String = "",
    val filmos: List<Filmography> = emptyList()
)

data class Filmography(
    @SerializedName("filmo") val filmographyTitle: String,
    @SerializedName("filmo_id") val filmographyId: Long
)
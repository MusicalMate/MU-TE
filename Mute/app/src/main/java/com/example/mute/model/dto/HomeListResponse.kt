package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class HomeListResponse(
    val actorListInfo: List<ActorInfo>,
    val musicalList: List<MusicalInfo>
)

data class ActorInfo(
    @SerializedName("actorPlaylistId") val actorPlaylistId: String,
    @SerializedName("actorPosterUrl") val actorPosterUrl: String,
    @SerializedName("star") val star: String,
    @SerializedName("actorId") val actorId: String,
    @SerializedName("actorName") val actorName: String
)

data class MusicalInfo(
    @SerializedName("musicalPlaylistId") val musicalPlaylistId: String,
    @SerializedName("musicalPosterUrl") val musicalPosterUrl: String,
    @SerializedName("star") val star: String,
    @SerializedName("musicalId") val musicalId: String,
    @SerializedName("musicalTitle") val musicalTitle: String
)
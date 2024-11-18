package com.example.mute.model.dto

import com.example.mute.model.Actor
import com.google.gson.annotations.SerializedName

data class PostSearchResponse(
    @SerializedName("type") val type: String = "",
    @SerializedName("star") val star: String = "false",

    @SerializedName("actorId") val actorId: Long? = null,
    @SerializedName("actorName") val actorName: String? = null,
    @SerializedName("actorUrl") val actorUrl: String? = null,
    @SerializedName("actorDescribe") val actorDescribe: String? = null,
    @SerializedName("actorFilmo") val actorFilmo: List<Filmo>? = null,

    @SerializedName("musicalId") val musicalId: Long? = null,
    @SerializedName("musicalTitle") val musicalTitle: String? = null,
    @SerializedName("musicalUrl") val musicalUrl: String? = null,
    @SerializedName("actors") val actors: List<Actor>? = null,
    @SerializedName("musicalDescribe") val musicalDescribe: String? = null,
    @SerializedName("musicalTime") val musicalTime: String? = null,

    @SerializedName("imageCoverUrls") val imageCoverUrls: List<String> = emptyList(),
    @SerializedName("imageId") val imageIds: List<String> = emptyList(),
    @SerializedName("imageTitle") val imageTitles: List<String> = emptyList(),
    @SerializedName("videoCoverUrls") val videoCoverUrls: List<String> = emptyList(),
    @SerializedName("videoId") val videoIds: List<String> = emptyList(),
    @SerializedName("videoTitle") val videoTitles: List<String> = emptyList()
)

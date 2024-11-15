package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class ActorPlayListResponse(
    @SerializedName("actorId") val actorId: Long,
    @SerializedName("actorName") val actorName: String,
    @SerializedName("actorUrl") val actorUrl: String,
    @SerializedName("actorDescribe") val actorDescribe: String,
    @SerializedName("actorFilmo") val actorFilmo: List<Filmo>,
    @SerializedName("imageCoverUrls") val imageCoverUrls: List<String>,
    @SerializedName("imageId") val imageIds: List<String>,
    @SerializedName("imageTitle") val imageTitles: List<String>,
    @SerializedName("videoCoverUrls") val videoCoverUrls: List<String>,
    @SerializedName("videoId") val videoIds: List<String>,
    @SerializedName("videoTitle") val videoTitles: List<String>
)

data class Filmo(
    @SerializedName("title") val title: String
)
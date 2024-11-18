package com.example.mute.model.dto

import com.example.mute.model.Actor
import com.google.gson.annotations.SerializedName

data class MusicalPlayListResponse(
    @SerializedName("musicalId") val musicalId: Long,
    @SerializedName("star") val star: String,
    @SerializedName("musicalTitle") val musicalTitle: String,
    @SerializedName("musicalUrl") val musicalUrl: String,
    @SerializedName("actors") val actors: List<Actor>,
    @SerializedName("musicalDescribe") val musicalDescribe: String,
    @SerializedName("musicalTime") val musicalTime: String,
    @SerializedName("videoCoverUrls") val videoCoverUrls: List<String>,
    @SerializedName("videoId") val videoIds: List<String>,
    @SerializedName("videoTitle") val videoTitles: List<String>,
    @SerializedName("imageCoverUrls") val imageCoverUrls: List<String>,
    @SerializedName("imageId") val imageIds: List<String>,
    @SerializedName("imageTitle") val imageTitles: List<String>
)
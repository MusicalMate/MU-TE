package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class FileMetaRequest(
    @SerializedName("fileTitle") val fileTitle: String,
    @SerializedName("fileDescription") val fileDescription: String,
    @SerializedName("musicalId") val musicalId: Long,
    @SerializedName("actorId") val actorId: List<Long>,
    @SerializedName("fileDate") val fileDate: String,
    @SerializedName("hashtag") val hashtag: List<String>
)

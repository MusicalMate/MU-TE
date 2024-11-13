package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class FileMetaInfo(
    @SerializedName("description") val description: String? = null,
    @SerializedName("keyName") val keyName: String? = null,
    @SerializedName("musicalTitle") val musicalTitle: String? = null,
    @SerializedName("time") val dateTime: String? = null,
    @SerializedName("actorId") val actorId: String? = null
)

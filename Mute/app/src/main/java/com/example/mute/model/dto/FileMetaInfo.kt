package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class FileMetaInfo(
    @SerializedName("description") val description: String,
    @SerializedName("keyName") val keyName: String,
    @SerializedName("musicalTitle") val musicalTitle: String,
    @SerializedName("dateTime") val dateTime: String,
    @SerializedName("actorId") val actorId: String
)

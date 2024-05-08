package com.example.mute

import com.google.gson.annotations.SerializedName

data class MusicalDetailInfo(
    val musicalId: Long = 0,
    val musicalTitle: String = "",
    val musicalTime: String = "",
    val musicalDescription: String = "",
    val actors: List<Actor> = emptyList(),
    val musicalImg: String? = ""
)

data class Actor(
    @SerializedName("ac") val name: String,
    @SerializedName("ac_id") val acId: Long
)

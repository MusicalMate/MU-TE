package com.example.mute.model

data class MusicalDetailInfo(
    val musicalPlayListId: Long = -1,
    val musicalImageUrl: String = "",
    val musicalTitle: String = "",
    val musicalTime: String = "",
    val musicalDescription: String = "",
    val actors: List<Actor> = emptyList(),
    val videoInfo: List<ContentInfo> = emptyList(),
    val imageInfo: List<ContentInfo> = emptyList()
)

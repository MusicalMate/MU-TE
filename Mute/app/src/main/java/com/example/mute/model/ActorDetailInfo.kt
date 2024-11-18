package com.example.mute.model

data class ActorDetailInfo(
    val actorPlayListId: Long = -1,
    val star: Boolean = false,
    val actorImageUrl: String = "",
    val actorName: String = "",
    val actorDescription: String = "",
    val actorFilmo: List<String> = emptyList(),
    val videoInfo: List<ContentInfo> = emptyList(),
    val imageInfo: List<ContentInfo> = emptyList()
)
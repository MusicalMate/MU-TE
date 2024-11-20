package com.example.mute.model

data class SearchResult (
    val type: String = "",
    val playListId: Long = -1,
    val name: String = "",
    val url: String = "",
    val videoInfo: List<ContentInfo> = emptyList(),
    val imageInfo: List<ContentInfo> = emptyList()
)
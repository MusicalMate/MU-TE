package com.example.mute.model

data class ContentDetailInfo(
    val actors: List<String> = emptyList(),
    val contentUrl: String = "",
    val description: String = "",
    val contentTime: String = "",
    val contentTitle: String = "",
    val performanceTitle: String = ""
)

package com.example.mute.model

data class UserInfo(
    val name: String = "",
    val email: String = "",
    val profile: String = "",
    val imageList: List<ContentInfo> = emptyList(),
    val videoList: List<ContentInfo> = emptyList()
)

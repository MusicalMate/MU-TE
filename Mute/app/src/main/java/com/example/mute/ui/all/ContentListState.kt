package com.example.mute.ui.all

import com.example.mute.model.ContentInfo

data class ContentListState(
    val contentList: List<ContentInfo> = emptyList(),
    val star: Boolean = false,
    val playList: Long = -1,
    val name: String = ""
)

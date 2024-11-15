package com.example.mute.ui

import com.example.mute.model.ContentInfo

fun interface ContentItemClickListener {

    fun onClick(contentInfo: ContentInfo)
}
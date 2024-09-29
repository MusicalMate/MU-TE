package com.example.mute.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContentItem(
    val thumbnail: String,
    val contentTitle: String,
    val contentLink: String
) : Parcelable

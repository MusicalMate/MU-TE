package com.example.mute.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContentInfo(
    val contentCoverKey: String = "",
    val contentId: String = "-1",
    val contentTitle: String = "",
    val contentType: ContentType
) : Parcelable
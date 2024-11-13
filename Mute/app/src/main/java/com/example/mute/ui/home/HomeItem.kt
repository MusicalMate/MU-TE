package com.example.mute.ui.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeItem(
    val img: String,
    val name: String,
    val star: String,
    val itemId: String,
    val playListId: String,
    val itemType: ItemType
) : Parcelable
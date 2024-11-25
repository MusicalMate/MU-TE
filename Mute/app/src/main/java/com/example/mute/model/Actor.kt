package com.example.mute.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Actor(
    @SerializedName("actorId") val actorId: String = "-1",
    @SerializedName("actorUrl") val actorUrl: String = "",
    @SerializedName("actorName") val actorName: String = ""
): Parcelable
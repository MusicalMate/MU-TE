package com.example.mute.model.dto

import com.google.gson.annotations.SerializedName

data class PostMyPageResponse(
    @SerializedName("uploadedImageList") val uploadedImageList: UploadedImageList,
    @SerializedName("uploadedVideoList") val uploadedVideoList: UploadedVideoList,
    @SerializedName("profile") val profile: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
)

data class UploadedImageList(
    @SerializedName("imageCoverUrls") val imageCoverUrls: List<String>,
    @SerializedName("imageId") val imageIds: List<Long>,
    @SerializedName("imageTitle") val imageTitles: List<String>
)

data class UploadedVideoList(
    @SerializedName("videoCoverUrls") val videoCoverUrls: List<String>,
    @SerializedName("videoId") val videoIds: List<Long>,
    @SerializedName("videoTitle") val videoTitles: List<String>
)

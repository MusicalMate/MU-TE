package com.example.mute.model

object DataParseUtil {

    fun parseVideoStringsToList(
        videoIds: List<String>,
        videoCoverUrls: List<String>,
        videoTitles: List<String>,
    ): List<ContentInfo> {
        val videoInfoList = videoIds.mapIndexed { index, id ->
            ContentInfo(
                contentId = id,
                contentCoverKey = videoCoverUrls.getOrNull(index) ?: "",
                contentTitle = videoTitles.getOrNull(index) ?: "",
                contentType = ContentType.VIDEO
            )
        }
        return videoInfoList
    }

    fun parseImageStringsToList(
        imageIds: List<String>,
        imageCoverUrls: List<String>,
        imageTitles: List<String>
    ): List<ContentInfo> {
        val imageInfoList = imageIds.mapIndexed { index, id ->
            ContentInfo(
                contentId = id,
                contentCoverKey = imageCoverUrls.getOrNull(index) ?: "",
                contentTitle = imageTitles.getOrNull(index) ?: "",
                contentType = ContentType.VIDEO
            )
        }
        return imageInfoList
    }
}
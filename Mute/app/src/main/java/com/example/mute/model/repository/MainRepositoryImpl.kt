package com.example.mute.model.repository

import com.example.mute.model.ActorDetailInfo
import com.example.mute.model.DataParseUtil
import com.example.mute.model.MainApi
import com.example.mute.model.MusicalDetailInfo
import com.example.mute.model.dto.ActorInfo
import com.example.mute.model.dto.FileMetaInfo
import com.example.mute.model.dto.HomeListResponse
import com.example.mute.model.dto.MusicalInfo
import com.example.mute.model.dto.PostImageUploadResponse
import com.example.mute.model.dto.PostVideoUploadResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainApi: MainApi
) : MainRepository {

    override fun getHomeInfo(): Flow<HomeListResponse> = flow {
        val response = mainApi.getHomeList()

        val actorList = response.getOrNull(0)?.map { actorInfo ->
            ActorInfo(
                actorPlaylistId = actorInfo["actorPlaylistId"] as String,
                actorPosterUrl = actorInfo["actorUrl"] as String,
                star = actorInfo["star"] as String,
                actorId = actorInfo["actorId"] as String,
                actorName = actorInfo["actorName"] as String
            )
        } ?: emptyList()

        val musicalList = response.getOrNull(1)?.map { musicalInfo ->
            MusicalInfo(
                musicalPlaylistId = musicalInfo["musicalPlaylistId"] as String,
                musicalPosterUrl = musicalInfo["musicalPosterUrl"] as String,
                star = musicalInfo["star"] as String,
                musicalId = musicalInfo["musicalId"] as String,
                musicalTitle = musicalInfo["musicalTitle"] as String
            )
        } ?: emptyList()

        emit(HomeListResponse(actorListInfo = actorList, musicalList = musicalList))
    }

    override fun getActorInfo(actorPlayListId: String): Flow<ActorDetailInfo> = flow {
        val response = mainApi.getActorPlayList(actorPlayListId.toLong())

        val actorDetailInfo = ActorDetailInfo(
            actorPlayListId = response.actorId,
            actorImageUrl = response.actorUrl,
            actorName = response.actorName,
            actorDescription = response.actorDescribe,
            actorFilmo = response.actorFilmo.map { it.title },
            videoInfo = DataParseUtil.parseVideoStringsToList(
                response.videoIds,
                response.videoCoverUrls,
                response.videoTitles
            ),
            imageInfo = DataParseUtil.parseImageStringsToList(
                response.imageIds,
                response.imageCoverUrls,
                response.imageTitles
            )
        )

        emit(actorDetailInfo)
    }

    override fun getMusicalInfo(musicalPlayListId: String): Flow<MusicalDetailInfo> = flow {
        val response = mainApi.getMusicalPlayList(musicalPlayListId.toLong())

        val musicalDetailInfo = MusicalDetailInfo(
            musicalPlayListId = response.musicalId,
            musicalImageUrl = response.musicalUrl,
            musicalTitle = response.musicalTitle,
            musicalTime = response.musicalTime,
            musicalDescription = response.musicalDescribe,
            actors = response.actors,
            videoInfo = DataParseUtil.parseVideoStringsToList(
                response.videoIds,
                response.videoCoverUrls,
                response.videoTitles
            ),
            imageInfo = DataParseUtil.parseImageStringsToList(
                response.imageIds,
                response.imageCoverUrls,
                response.imageTitles
            )
        )
        emit(musicalDetailInfo)
    }

    override fun uploadImage(imageFile: File, fileMeta: FileMetaInfo): Flow<String> = flow {
        val fileMetaInfoList = listOf(
            FileMetaInfo(description = fileMeta.description),
            FileMetaInfo(keyName = fileMeta.keyName),
            FileMetaInfo(musicalTitle = fileMeta.musicalTitle),
            FileMetaInfo(dateTime = fileMeta.dateTime),
            FileMetaInfo(actorId = fileMeta.actorId)
        )
        val response = mainApi.postImageInfo(fileMetaInfoList)

        val url = response.s3url
        val requestBody = imageFile.asRequestBody("application/octet-stream".toMediaTypeOrNull())
        val uploadResponse = mainApi.uploadFile(url, requestBody)

        if (uploadResponse.isSuccessful) {
            val result = mainApi.postUploadImageResponse(
                PostImageUploadResponse(
                    "success",
                    response.imageId
                )
            )
            emit("success")
        } else {
            mainApi.postUploadImageResponse(
                PostImageUploadResponse(
                    "fail",
                    response.imageId
                )
            )
            emit("fail")
        }
    }

    override fun uploadVideo(videoFile: File, fileMeta: FileMetaInfo): Flow<String> = flow {
        val fileMetaInfoList = listOf(
            FileMetaInfo(description = fileMeta.description),
            FileMetaInfo(keyName = fileMeta.keyName),
            FileMetaInfo(musicalTitle = fileMeta.musicalTitle),
            FileMetaInfo(dateTime = fileMeta.dateTime),
            FileMetaInfo(actorId = fileMeta.actorId)
        )
        val response = mainApi.postVideoInfo(fileMetaInfoList)

        val url = response.s3url
        val requestBody = videoFile.asRequestBody("application/octet-stream".toMediaTypeOrNull())
        val uploadResponse = mainApi.uploadFile(url, requestBody)

        if (uploadResponse.isSuccessful) {
            mainApi.postUploadVideoResponse(
                PostVideoUploadResponse(
                    "success",
                    response.videoId
                )
            )
            emit("success")
        } else {
            mainApi.postUploadVideoResponse(
                PostVideoUploadResponse(
                    "fail",
                    response.videoId
                )
            )
            emit("fail")
        }
    }
}
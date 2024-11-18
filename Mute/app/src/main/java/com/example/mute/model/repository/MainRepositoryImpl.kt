package com.example.mute.model.repository

import android.util.Log
import com.example.mute.model.Actor
import com.example.mute.model.ActorDetailInfo
import com.example.mute.model.ContentDetailInfo
import com.example.mute.model.DataParseUtil
import com.example.mute.model.MainApi
import com.example.mute.model.MusicalDetailInfo
import com.example.mute.model.UserInfo
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
            star = response.star == "true",
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
            star = response.star == "true",
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

    override fun getMusicalActorList(musicalTitle: String): Flow<List<Actor>> = flow {
        emit(mainApi.getMusicalActors(musicalTitle).actors)
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

        Log.e("이미지 업로드 response", response.toString())
        Log.e("이미지 업로드 이미지타입", imageFile.extension.lowercase())

        val requestBody =
            imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        //val requestBody = imageFile.asRequestBody("application/octet-stream".toMediaTypeOrNull())
        val uploadResponse = mainApi.uploadFile(url, requestBody)

        Log.e("이미지 업로드 결과", uploadResponse.toString())

        if (uploadResponse.isSuccessful) {
            val result = mainApi.postUploadImageResponse(
                PostImageUploadResponse(
                    "success",
                    response.imageId
                )
            )

            Log.e("이미지 업로드 result", result.toString())
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

        Log.e("이미지 업로드 response", response.toString())
        Log.e("이미지 업로드 이미지타입", videoFile.extension.lowercase())

        val url = response.s3url
        val requestBody = videoFile.asRequestBody("video/*".toMediaTypeOrNull())
        val uploadResponse = mainApi.uploadFile(url, requestBody)

        Log.e("이미지 업로드 결과", uploadResponse.toString())

        if (uploadResponse.isSuccessful) {
            val result = mainApi.postUploadVideoResponse(
                PostVideoUploadResponse(
                    "success",
                    response.videoId
                )
            )

            Log.e("이미지 업로드 result", result.toString())
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

    override fun getStreamingImage(imageId: String): Flow<ContentDetailInfo> = flow {
        val response = mainApi.postStreamingImage(imageId.toLong())
        val contentDetailInfo = ContentDetailInfo(
            actors = response.actors,
            contentUrl = response.presignedUrl[0],
            description = response.description,
            contentTime = response.imageTime,
            contentTitle = response.imageTitle,
            performanceTitle = response.musicalTitle
        )
        emit(contentDetailInfo)
    }

    override fun getStreamingVideo(videoId: String): Flow<ContentDetailInfo> = flow {
        val response = mainApi.postStreamingVideo(videoId.toLong())
        val contentDetailInfo = ContentDetailInfo(
            actors = response.actors,
            contentUrl = response.presignedUrls[0],
            description = response.description,
            contentTime = response.videoTime,
            contentTitle = response.videoTitle,
            performanceTitle = response.musicalTitle
        )
        emit(contentDetailInfo)
    }

    override fun updateActorFavorite(actorPlayListId: Long): Flow<Boolean> = flow {
        val response = mainApi.postActorFavoriteStatus(actorPlayListId)
        Log.e("mute_update_actor", response.toString())

        if (response.star == "true") emit(true)
        else emit(false)
    }

    override fun updateMusicalFavorite(musicalPlayListId: Long): Flow<Boolean> = flow {
        val response = mainApi.postMusicalFavoriteStatus(musicalPlayListId)
        if (response.star == "true") emit(true)
        else emit(false)
    }

    override fun searchKeyword(keyword: String): Flow<String> = flow {
        val response = mainApi.postSearchKeyword(keyword)

        Log.e("mute_search_keyword", response.toString())

    }

    override fun getMyPageInfo(): Flow<UserInfo> = flow {
        val response = mainApi.postMyPageInfo()
        val userInfo = UserInfo(
            name = response.name,
            email = response.email,
            profile = response.profile,
            imageList = DataParseUtil.parseImageStringsToList(
                response.uploadedImageList.imageIds.map { it.toString() },
                response.uploadedImageList.imageCoverUrls,
                response.uploadedImageList.imageTitles
            ),
            videoList = DataParseUtil.parseVideoStringsToList(
                response.uploadedVideoList.videoIds.map { it.toString() },
                response.uploadedVideoList.videoCoverUrls,
                response.uploadedVideoList.videoTitles
            )
        )
        emit(userInfo)
    }
}
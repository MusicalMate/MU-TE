package com.example.mute.model.repository

import com.example.mute.Actor
import com.example.mute.ActorDetailInfo
import com.example.mute.Filmography
import com.example.mute.MusicalDetailInfo
import com.example.mute.model.MainApi
import com.example.mute.model.dto.ActorInfo
import com.example.mute.model.dto.ActorPlayListResponse
import com.example.mute.model.dto.FileMetaInfo
import com.example.mute.model.dto.HomeListResponse
import com.example.mute.model.dto.MusicalInfo
import com.example.mute.model.dto.MusicalPlayListResponse
import com.example.mute.model.dto.PostImageUploadResponse
import com.example.mute.model.dto.PostVideoUploadResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainApi: MainApi
) : MainRepository {

    private val gson = Gson()

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

    override fun getActorInfo(actorId: String): Flow<ActorPlayListResponse> = flow {
        emit(mainApi.getActorPlayList(actorId))
    }

    override fun getMusicalInfo(musicalId: String): Flow<MusicalPlayListResponse> = flow {
        emit(mainApi.getMusicalPlayList(musicalId))
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
            mainApi.postUploadImageResponse(PostImageUploadResponse("success", response.imageId))
            emit("success")
        } else {
            mainApi.postUploadImageResponse(PostImageUploadResponse("fail", response.imageId))
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
            mainApi.postUploadVideoResponse(PostVideoUploadResponse("success", response.videoId))
            emit("success")
        } else {
            mainApi.postUploadVideoResponse(PostVideoUploadResponse("fail", response.videoId))
            emit("fail")
        }
    }

    override fun searchMusical(musicalTitle: String): Flow<MusicalDetailInfo> = flow {
        val response = mainApi.postSearchMusical(musicalTitle)
        val listType = object : TypeToken<List<Actor>>() {}.type
        val musicalDetailInfo =
            MusicalDetailInfo(
                musicalId = response.musicalId,
                musicalTitle = response.musicalTitle,
                musicalTime = response.musicalTime,
                musicalDescription = response.musicalDescription,
                musicalImg = response.musicalImg,
                actors = gson.fromJson(response.actors, listType)
            )
        emit(musicalDetailInfo)
    }

    override fun searchActor(actorName: String): Flow<ActorDetailInfo> = flow {
        val response = mainApi.postSearchActor(actorName)
        val listType = object : TypeToken<List<Filmography>>() {}.type
        val actorDetailInfo = ActorDetailInfo(
            actorId = response.actorId,
            actorDescription = response.actorDescription,
            actorName = response.actorName,
            filmos = gson.fromJson(response.filmo, listType),
            actorImg = response.actorImg
        )
        emit(actorDetailInfo)
    }
}
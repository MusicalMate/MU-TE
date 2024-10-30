package com.example.mute.model.repository

import android.util.Log
import com.example.mute.Actor
import com.example.mute.ActorDetailInfo
import com.example.mute.Filmography
import com.example.mute.MusicalDetailInfo
import com.example.mute.model.MainApi
import com.example.mute.model.dto.ActorPlayListResponse
import com.example.mute.model.dto.FileMetaInfo
import com.example.mute.model.dto.HomeListResponse
import com.example.mute.model.dto.MusicalPlayListResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainApi: MainApi
) : MainRepository {

    private val gson = Gson()

    override fun getHomeInfo(): Flow<HomeListResponse> = flow{
        emit(mainApi.getHomeList())
    }

    override fun getActorInfo(actorId: Long): Flow<ActorPlayListResponse> = flow{
        emit(mainApi.getActorPlayList(actorId.toString()))
    }

    override fun getMusicalInfo(musicalId: Long): Flow<MusicalPlayListResponse> = flow{
        emit(mainApi.getMusicalPlayList(musicalId.toString()))
    }

    override fun uploadImage(imageFile: File, fileMeta: FileMetaInfo): Flow<String> = flow{
        val response = mainApi.postImageInfo(fileMeta)
        if(!response.isSuccessful){
            throw Exception("Send Failed")
        }

        val url = response.body() ?: throw Exception("No Url")
        val requestFile = imageFile.asRequestBody("image/*".toMediaType())
        val body = MultipartBody.Part.createFormData("imageFile", fileMeta.keyName, requestFile)
        val uploadResponse = mainApi.uploadFile(url, body)
        if(uploadResponse.isSuccessful){
            emit("success")
        } else{
            emit("fail")
        }
    }

    override fun uploadVideo(videoFile: File, fileMeta: FileMetaInfo): Flow<String> = flow{
        val response = mainApi.postVideoInfo(fileMeta)
        if(!response.isSuccessful){
            throw Exception("Send Failed")
        }

        val url = response.body() ?: throw Exception("No Url")
        val requestFile = videoFile.asRequestBody("video/*".toMediaType())
        val body = MultipartBody.Part.createFormData("videoFile", fileMeta.keyName, requestFile)
        val uploadResponse = mainApi.uploadFile(url, body)
        if(uploadResponse.isSuccessful){
            emit("success")
        } else{
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
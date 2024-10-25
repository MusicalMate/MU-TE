package com.example.mute.model.repository

import android.util.Log
import com.example.mute.Actor
import com.example.mute.ActorDetailInfo
import com.example.mute.Filmography
import com.example.mute.MusicalDetailInfo
import com.example.mute.model.MainApi
import com.example.mute.model.dto.FileMetaRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainApi: MainApi
) : MainRepository {

    private val gson = Gson()

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

    override fun uploadSmallFiles(
        bigFiles: List<MultipartBody.Part?>,
        smallFiles: List<MultipartBody.Part?>,
        fileMeta: FileMetaRequest
    ): Flow<String> = flow {
        Log.d("uploadSmallFiles_fileMeta", fileMeta.toString())
        emit(mainApi.postUploadSmall("image", 1, fileMeta, bigFiles, smallFiles))
    }
}
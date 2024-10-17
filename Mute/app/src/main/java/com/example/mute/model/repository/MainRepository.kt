package com.example.mute.model.repository

import android.util.Log
import com.example.mute.Actor
import com.example.mute.ActorDetailInfo
import com.example.mute.Filmography
import com.example.mute.MusicalDetailInfo
import com.example.mute.model.dto.FileMetaRequest
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody

interface MainRepository {

    fun searchMusical(musicalTitle: String): Flow<MusicalDetailInfo>

    fun searchActor(actorName: String): Flow<ActorDetailInfo>

    fun uploadSmallFiles(
        bigFiles: List<MultipartBody.Part?>,
        smallFiles: List<MultipartBody.Part?>,
        fileMeta: FileMetaRequest
    ): Flow<String>
}
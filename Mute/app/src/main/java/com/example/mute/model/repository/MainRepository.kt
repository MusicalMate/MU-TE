package com.example.mute.model.repository

import com.example.mute.ActorDetailInfo
import com.example.mute.MusicalDetailInfo
import com.example.mute.model.dto.ActorPlayListResponse
import com.example.mute.model.dto.FileMetaInfo
import com.example.mute.model.dto.HomeListResponse
import com.example.mute.model.dto.MusicalPlayListResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import java.io.File

interface MainRepository {

    fun getHomeInfo(): Flow<HomeListResponse>

    fun getActorInfo(actorId: String): Flow<ActorPlayListResponse>

    fun getMusicalInfo(musicalId: String): Flow<MusicalPlayListResponse>

    fun uploadImage(imageFile: File, fileMeta: FileMetaInfo): Flow<String>

    fun uploadVideo(videoFile: File, fileMeta: FileMetaInfo): Flow<String>

    fun searchMusical(musicalTitle: String): Flow<MusicalDetailInfo>

    fun searchActor(actorName: String): Flow<ActorDetailInfo>
}
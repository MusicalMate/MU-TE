package com.example.mute.model.repository

import com.example.mute.model.ActorDetailInfo
import com.example.mute.model.MusicalDetailInfo
import com.example.mute.model.dto.FileMetaInfo
import com.example.mute.model.dto.HomeListResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

interface MainRepository {

    fun getHomeInfo(): Flow<HomeListResponse>

    fun getActorInfo(actorPlayListId: String): Flow<ActorDetailInfo>

    fun getMusicalInfo(musicalPlayListId: String): Flow<MusicalDetailInfo>

    fun uploadImage(imageFile: File, fileMeta: FileMetaInfo): Flow<String>

    fun uploadVideo(videoFile: File, fileMeta: FileMetaInfo): Flow<String>
}
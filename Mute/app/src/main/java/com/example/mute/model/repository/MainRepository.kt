package com.example.mute.model.repository

import com.example.mute.model.Actor
import com.example.mute.model.ActorDetailInfo
import com.example.mute.model.ContentDetailInfo
import com.example.mute.model.MusicalDetailInfo
import com.example.mute.model.SearchResult
import com.example.mute.model.UserInfo
import com.example.mute.model.dto.FileMetaInfo
import com.example.mute.model.dto.HomeListResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

interface MainRepository {

    fun getHomeInfo(): Flow<HomeListResponse>

    fun getActorInfo(actorPlayListId: String): Flow<ActorDetailInfo>

    fun getMusicalInfo(musicalPlayListId: String): Flow<MusicalDetailInfo>

    fun getMusicalActorList(musicalTitle: String): Flow<List<Actor>>

    fun uploadImage(imageFile: File, fileMeta: FileMetaInfo): Flow<String>

    fun uploadVideo(videoFile: File, fileMeta: FileMetaInfo): Flow<String>

    fun getStreamingImage(imageId: String): Flow<ContentDetailInfo>

    fun getStreamingVideo(videoId: String): Flow<ContentDetailInfo>

    fun updateActorFavorite(actorPlayListId: Long): Flow<Boolean>

    fun updateMusicalFavorite(musicalPlayListId: Long): Flow<Boolean>

    fun searchKeyword(keyword: String): Flow<SearchResult>

    fun getMyPageInfo(): Flow<UserInfo>
}
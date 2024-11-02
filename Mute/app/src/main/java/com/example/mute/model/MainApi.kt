package com.example.mute.model

import com.example.mute.model.dto.ActorPlayListResponse
import com.example.mute.model.dto.FileMetaInfo
import com.example.mute.model.dto.HomeListResponse
import com.example.mute.model.dto.MusicalPlayListResponse
import com.example.mute.model.dto.SearchActorResponse
import com.example.mute.model.dto.SearchMusicalResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface MainApi {

    @POST("/api/home/lists")
    suspend fun getHomeList(): HomeListResponse

    @GET("/api/actorplaylist/{actorListId}")
    suspend fun getActorPlayList(@Path(value = "actorListId") type: String): ActorPlayListResponse

    @GET("/api/musicalplaylist/{musicalListId}")
    suspend fun getMusicalPlayList(@Path(value = "musicalListId") type: String): MusicalPlayListResponse

    @POST("/api/upload/image")
    suspend fun postImageInfo(@Body fileMeta: FileMetaInfo): Response<String>

    @POST("/api/upload/video")
    suspend fun postVideoInfo(@Body fileMeta: FileMetaInfo): Response<String>

    @Multipart
    @PUT
    suspend fun uploadFile(@Url url: String, @Part file: MultipartBody.Part): Response<String>

    @POST("/api/search/musical")
    suspend fun postSearchMusical(@Query("title") musicalTitle: String): SearchMusicalResponse

    @POST("/api/search/actor")
    suspend fun postSearchActor(@Query("name") actorName: String): SearchActorResponse
}
package com.example.mute.model

import com.example.mute.model.dto.ActorPlayListResponse
import com.example.mute.model.dto.FileMetaInfo
import com.example.mute.model.dto.MusicalPlayListResponse
import com.example.mute.model.dto.PostImageResponse
import com.example.mute.model.dto.PostImageUploadResponse
import com.example.mute.model.dto.PostVideoResponse
import com.example.mute.model.dto.PostVideoUploadResponse
import com.example.mute.model.dto.SearchActorResponse
import com.example.mute.model.dto.SearchMusicalResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface MainApi {

    @POST("/api/home/lists")
    suspend fun getHomeList(): List<List<Map<String, String>>>

    @POST("/api/actorplaylist")
    suspend fun getActorPlayList(@Query(value = "actorListId") type: Long): ActorPlayListResponse

    @POST("/api/musicalplaylist")
    suspend fun getMusicalPlayList(@Query(value = "musicalListId") type: Long): MusicalPlayListResponse

    @POST("/api/upload/image")
    suspend fun postImageInfo(@Body fileMeta: List<FileMetaInfo>): PostImageResponse

    @POST("/api/upload/image/valid")
    suspend fun postUploadImageResponse(@Body postImageUploadResponse: PostImageUploadResponse): Response<ResponseBody>

    @POST("/api/upload/video")
    suspend fun postVideoInfo(@Body fileMeta: List<FileMetaInfo>): PostVideoResponse

    @POST("/api/upload/video/valid")
    suspend fun postUploadVideoResponse(@Body postVideoUploadResponse: PostVideoUploadResponse): Response<ResponseBody>

    @PUT
    suspend fun uploadFile(@Url url: String, @Body file: RequestBody): Response<ResponseBody>
}
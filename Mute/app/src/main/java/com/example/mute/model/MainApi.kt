package com.example.mute.model

import com.example.mute.model.dto.ActorPlayListResponse
import com.example.mute.model.dto.FavoriteStateResponse
import com.example.mute.model.dto.FileMetaInfo
import com.example.mute.model.dto.MusicalActors
import com.example.mute.model.dto.MusicalPlayListResponse
import com.example.mute.model.dto.PostImageResponse
import com.example.mute.model.dto.PostImageUploadResponse
import com.example.mute.model.dto.PostMyPageResponse
import com.example.mute.model.dto.PostSearchResponse
import com.example.mute.model.dto.PostVideoResponse
import com.example.mute.model.dto.PostVideoUploadResponse
import com.example.mute.model.dto.StreamingImageResponse
import com.example.mute.model.dto.StreamingVideoResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.Url

interface MainApi {

    @POST("/api/home/lists")
    suspend fun getHomeList(): List<List<Map<String, String>>>

    @POST("/api/actorplaylist")
    suspend fun getActorPlayList(@Query(value = "actorListId") type: Long): ActorPlayListResponse

    @POST("/api/musicalplaylist")
    suspend fun getMusicalPlayList(@Query(value = "musicalListId") type: Long): MusicalPlayListResponse

    @POST("/api/upload/actor/detail")
    suspend fun getMusicalActors(@Query(value = "musicalTitle") type: String): MusicalActors

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

    @POST("/api/streaming/image")
    suspend fun postStreamingImage(@Query(value = "imageId") type: Long): StreamingImageResponse

    @POST("/api/streaming/video")
    suspend fun postStreamingVideo(@Query(value = "videoId") type: Long): StreamingVideoResponse

    @POST("/api/star/actor")
    suspend fun postActorFavoriteState(@Query(value = "actorPlaylistId") type: Long): FavoriteStateResponse

    @POST("/api/star/musical")
    suspend fun postMusicalFavoriteState(@Query(value = "musicalPlaylistId") type: Long): FavoriteStateResponse

    @POST("/api/search")
    suspend fun postSearchKeyword(@Query(value = "param") type: String): PostSearchResponse

    @POST("/api/mypage")
    suspend fun postMyPageInfo(): PostMyPageResponse

    @POST("/api/delete/image")
    suspend fun postDeleteImage(@Query(value = "imageId") type: String): ResponseBody

    @POST("/api/delete/video")
    suspend fun postDeleteVideo(@Query(value = "videoId") type: String): ResponseBody
}
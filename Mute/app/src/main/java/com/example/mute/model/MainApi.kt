package com.example.mute.model

import com.example.mute.model.dto.FileMetaRequest
import com.example.mute.model.dto.SearchActorResponse
import com.example.mute.model.dto.SearchMusicalResponse
import okhttp3.MultipartBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {

    @POST("api/search/musical")
    suspend fun postSearchMusical(@Query("title") musicalTitle: String): SearchMusicalResponse

    @POST("api/search/actor")
    suspend fun postSearchActor(@Query("name") actorName: String): SearchActorResponse

    @Multipart
    @POST("api/upload/small/{type}")
    suspend fun postUploadSmall(
        @Path(value = "type") type: String,
        @Header("userId") userId: Long,
        @Part("fileMeta") fileMeta: FileMetaRequest,
        @Part bigFiles: List<MultipartBody.Part?>,
        @Part smallFiles: List<MultipartBody.Part?>
    ): String
}
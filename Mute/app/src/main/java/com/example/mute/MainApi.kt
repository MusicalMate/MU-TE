package com.example.mute

import retrofit2.http.POST
import retrofit2.http.Query

interface MainApi {

    @POST("api/search/musical")
    suspend fun postSearchMusical(@Query("title") musicalTitle: String): SearchMusicalResponse

    @POST("api/search/actor")
    suspend fun postSearchActor(@Query("name") actorName: String): SearchActorResponse
}
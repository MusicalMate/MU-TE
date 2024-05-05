package com.example.mute

class MainRepository {

    private val service = RetrofitInstance.getInstance().create(MainApi::class.java)

    suspend fun searchMusical(musicalTitle: String): SearchMusicalResponse =
        service.postSearchMusical(musicalTitle)

    suspend fun searchActor(actorName: String): SearchActorResponse =
        service.postSearchActor(actorName)
}
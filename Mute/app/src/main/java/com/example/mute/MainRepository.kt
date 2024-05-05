package com.example.mute

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository {

    private val service = RetrofitInstance.getInstance().create(MainApi::class.java)

    fun searchMusical(musicalTitle: String): Flow<SearchMusicalResponse> = flow {
        emit(service.postSearchMusical(musicalTitle))
    }

    fun searchActor(actorName: String): Flow<SearchActorResponse> = flow {
        emit(service.postSearchActor(actorName))
    }
}
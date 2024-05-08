package com.example.mute

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository {

    private val service = RetrofitInstance.getInstance().create(MainApi::class.java)
    private val gson = Gson()

    fun searchMusical(musicalTitle: String): Flow<MusicalDetailInfo> = flow {
        val response = service.postSearchMusical(musicalTitle)
        val listType = object : TypeToken<List<Actor>>() {}.type
        val musicalDetailInfo =
            MusicalDetailInfo(
                musicalId = response.musicalId,
                musicalTitle = response.musicalTitle,
                musicalTime = response.musicalTime,
                musicalDescription = response.musicalDescription,
                musicalImg = response.musicalImg,
                actors = gson.fromJson(response.actors, listType)
            )
        emit(musicalDetailInfo)
    }

    fun searchActor(actorName: String): Flow<ActorDetailInfo> = flow {
        val response = service.postSearchActor(actorName)
        val listType = object : TypeToken<List<Filmography>>() {}.type
        val actorDetailInfo = ActorDetailInfo(
            actorId = response.actorId,
            actorDescription = response.actorDescription,
            actorName = response.actorName,
            filmos = gson.fromJson(response.filmo, listType)
        )
        emit(actorDetailInfo)
    }
}
package com.example.mute.model.dto

data class HomeListResponse(
    val myListInfo: ListInfo,
    val actorListInfo: ListInfo,
    val musicalList: ListInfo
)

data class ListInfo(
    val image: String,
    val title: String,
    val myListId: Long
)
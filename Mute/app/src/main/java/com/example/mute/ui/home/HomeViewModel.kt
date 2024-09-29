package com.example.mute.ui.home

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val myList =
        listOf(HomeItem("", "리스트1", ItemType.MY_LIST), HomeItem("", "리스트2", ItemType.MY_LIST))
    val musicalList = listOf(
        HomeItem("", "구텐버그", ItemType.MUSICAL),
        HomeItem("", "스토리오브마이라이프", ItemType.MUSICAL),
        HomeItem("", "해적", ItemType.MUSICAL),
        HomeItem("", "하데스타운", ItemType.MUSICAL),
        HomeItem("", "Trace U", ItemType.MUSICAL)
    )
    val actorList = listOf(
        HomeItem("", "정욱진", ItemType.ACTOR),
        HomeItem("", "김려원", ItemType.ACTOR),
        HomeItem("", "김이후", ItemType.ACTOR),
        HomeItem("", "박강현", ItemType.ACTOR),
        HomeItem("", "기세중", ItemType.ACTOR),
        HomeItem("", "최호승", ItemType.ACTOR),
        HomeItem("", "최수진", ItemType.ACTOR)
    )

}
package com.example.mute.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mute.model.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

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

    fun getHomeInfo() {
        viewModelScope.launch {
            mainRepository.getHomeInfo().catch {
                Log.d("mute_get_home_info_error", it.message.toString())
            }.collect {
                Log.d("mute_get_home_info", it.toString())
            }
        }
    }
}
package com.example.mute.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mute.model.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val myList = emptyList<HomeItem>()

    private val _musicalList = MutableStateFlow<List<HomeItem>>(emptyList())
    val musicalList = _musicalList.asStateFlow()

    private val _actorList = MutableStateFlow<List<HomeItem>>(emptyList())
    val actorList = _actorList.asStateFlow()

    fun getHomeInfo() {
        viewModelScope.launch {
            mainRepository.getHomeInfo().catch {
                Log.d("mute_get_home_info_error", it.message.toString())
            }.collect { response ->
                _actorList.value = response.actorListInfo.map { actorInfo ->
                    HomeItem(
                        actorInfo.actorPosterUrl,
                        actorInfo.actorName,
                        actorInfo.star,
                        actorInfo.actorId,
                        actorInfo.actorPlaylistId,
                        ItemType.ACTOR
                    )
                }
                _musicalList.value = response.musicalList.map { musicalInfo ->
                    HomeItem(
                        musicalInfo.musicalPosterUrl,
                        musicalInfo.musicalTitle,
                        musicalInfo.star,
                        musicalInfo.musicalId,
                        musicalInfo.musicalPlaylistId,
                        ItemType.MUSICAL
                    )
                }
            }
        }
    }
}
package com.example.mute.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mute.model.ActorDetailInfo
import com.example.mute.model.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _actorDetailInfo = MutableStateFlow(ActorDetailInfo())
    val actorDetailInfo = _actorDetailInfo.asStateFlow()

    fun getActorInfo(actorPlayListId: String) {
        viewModelScope.launch {
            mainRepository.getActorInfo(actorPlayListId)
                .catch {
                    Log.d("mute_actor_detail_info", "getActorInfo error ${it.message}")
                }.collect { actorDetailInfo ->
                    _actorDetailInfo.value = actorDetailInfo
                }
        }
    }

    fun updateFavoriteState() {
        viewModelScope.launch {
            mainRepository.updateActorFavorite(actorDetailInfo.value.actorPlayListId)
                .catch {
                    Log.d("mute_actor_update_favorite", "updateFavoriteStatus error ${it.message}")
                }.collect { isFavorite ->
                    _actorDetailInfo.value = actorDetailInfo.value.copy(star = isFavorite)
                }
        }
    }
}
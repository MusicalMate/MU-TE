package com.example.mute.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mute.ActorDetailInfo
import com.example.mute.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ActorDetailViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _actorDetailInfo = MutableStateFlow(ActorDetailInfo())
    val actorDetailInfo: StateFlow<ActorDetailInfo> = _actorDetailInfo.asStateFlow()

    fun getActorInfo(name: String) {
        viewModelScope.launch {
            mainRepository.searchActor(name)
                .catch {
                    Log.d("mute_search_actor_error", "getActorInfo error ${it.message}")
                }.collect { actorDetailInfo ->
                    _actorDetailInfo.value = actorDetailInfo
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val mainRepository = MainRepository()
                ActorDetailViewModel(mainRepository)
            }
        }
    }
}
package com.example.mute.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mute.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

data class ActorDetailInfo(
    val actorId: Long = 0,
    val actorDescription: String = "",
    val actorName: String = "",
    val filmography: String = ""
)

class ActorDetailViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _actorDetailInfo = MutableStateFlow(ActorDetailInfo())
    val actorDetailInfo: StateFlow<ActorDetailInfo> = _actorDetailInfo.asStateFlow()

    fun getActorInfo(name: String) {
        viewModelScope.launch {
            mainRepository.searchActor(name)
                .catch {
                    Log.d("mute_search_actor_error", "getActorInfo error ${it.message}")
                }.collect { searchActorResponse ->
                    _actorDetailInfo.value = ActorDetailInfo(
                        actorId = searchActorResponse.actorId,
                        actorDescription = searchActorResponse.actorDescription,
                        actorName = searchActorResponse.actorName,
                        filmography = searchActorResponse.filmography
                    )
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
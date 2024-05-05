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

data class MusicalDetailInfo(
    val musicalId: Long = 0,
    val musicalTitle: String = "",
    val musicalTime: String = "",
    val musicalDescription: String = "",
    val actors: String = "",
    val musicalImg: String? = ""
)

class MusicalDetailViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _musicalDetailInfo = MutableStateFlow(MusicalDetailInfo())
    val musicalDetailInfo: StateFlow<MusicalDetailInfo> = _musicalDetailInfo.asStateFlow()

    fun getMusicalInfo(title: String) {
        viewModelScope.launch {
            mainRepository.searchMusical(title)
                .catch {
                    Log.d("mute_search_musical_error", "getMusicalInfo error ${it.message}")
                }.collect { searchMusicalResponse ->
                    _musicalDetailInfo.value =
                        MusicalDetailInfo(
                            musicalId = searchMusicalResponse.musicalId,
                            musicalTitle = searchMusicalResponse.musicalTitle,
                            musicalTime = searchMusicalResponse.musicalTime,
                            musicalDescription = searchMusicalResponse.musicalDescription,
                            actors = searchMusicalResponse.actors,
                            musicalImg = searchMusicalResponse.musicalImg
                        )
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val mainRepository = MainRepository()
                MusicalDetailViewModel(mainRepository)
            }
        }
    }
}
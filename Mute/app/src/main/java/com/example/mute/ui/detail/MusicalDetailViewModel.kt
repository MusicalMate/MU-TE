package com.example.mute.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mute.model.MusicalDetailInfo
import com.example.mute.model.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicalDetailViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _musicalDetailInfo = MutableStateFlow(MusicalDetailInfo())
    val musicalDetailInfo = _musicalDetailInfo.asStateFlow()

    fun getMusicalInfo(musicalPlayListId: String) {
        viewModelScope.launch {
            mainRepository.getMusicalInfo(musicalPlayListId)
                .catch {
                    Log.d("mute_musical_detail_info", "getMusicalInfo error ${it.message}")
                }.collect { musicalDetailInfo ->
                    _musicalDetailInfo.value = musicalDetailInfo
                }
        }
    }

    fun updateFavoriteStatus() {
        viewModelScope.launch {
            mainRepository.updateMusicalFavorite(musicalDetailInfo.value.musicalPlayListId)
                .catch {
                    Log.d(
                        "mute_musical_update_favorite",
                        "updateFavoriteStatus error ${it.message}"
                    )
                }.collect { isFavorite ->
                    _musicalDetailInfo.value = musicalDetailInfo.value.copy(star = isFavorite)
                }
        }
    }
}
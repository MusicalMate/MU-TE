package com.example.mute.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mute.model.repository.MainRepositoryImpl
import com.example.mute.MusicalDetailInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicalDetailViewModel @Inject constructor(private val mainRepository: MainRepositoryImpl) :
    ViewModel() {

    private val _musicalDetailInfo = MutableStateFlow(MusicalDetailInfo())
    val musicalDetailInfo: StateFlow<MusicalDetailInfo> = _musicalDetailInfo.asStateFlow()

    fun getMusicalInfo(title: String) {
        viewModelScope.launch {
            mainRepository.searchMusical(title)
                .catch {
                    Log.d("mute_search_musical_error", "getMusicalInfo error ${it.message}")
                }.collect { musicalDetailInfo ->
                    _musicalDetailInfo.value = musicalDetailInfo
                }
        }
    }
}
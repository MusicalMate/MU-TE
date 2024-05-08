package com.example.mute.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mute.MainRepository
import com.example.mute.MusicalDetailInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MusicalDetailViewModel(private val mainRepository: MainRepository) : ViewModel() {

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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val mainRepository = MainRepository()
                MusicalDetailViewModel(mainRepository)
            }
        }
    }
}
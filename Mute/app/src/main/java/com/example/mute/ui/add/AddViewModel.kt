package com.example.mute.ui.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mute.model.dto.FileMetaInfo
import com.example.mute.model.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _imagePath = MutableStateFlow("")
    val imagePath = _imagePath.asStateFlow()

    val mediaTitle = MutableStateFlow("")
    val mediaDescription = MutableStateFlow("")
    val hashTag = MutableStateFlow("")

    private val _performanceTitle = MutableStateFlow("")
    val performanceTitle = _performanceTitle.asStateFlow()

    private val _performanceTime = MutableStateFlow("")
    val performanceTime = _performanceTime.asStateFlow()

    private val _performanceActors = MutableStateFlow("")
    val performanceActors = _performanceActors.asStateFlow()

    fun setImage(absolutePath: String) {
        _imagePath.value = absolutePath
    }

    fun uploadFiles() {
        // 파일 업로드
    }
}
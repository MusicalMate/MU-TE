package com.example.mute.ui.add

import androidx.lifecycle.ViewModel
import com.example.mute.model.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddVideoViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _videoPath = MutableStateFlow("")
    val videoPath = _videoPath.asStateFlow()

    val mediaTitle = MutableStateFlow("")
    val mediaDescription = MutableStateFlow("")

    private val _performanceTitle = MutableStateFlow("")
    val performanceTitle = _performanceTitle.asStateFlow()

    private val _performanceTime = MutableStateFlow("")
    val performanceTime = _performanceTime.asStateFlow()

    private val _performanceActors = MutableStateFlow("")
    val performanceActors = _performanceActors.asStateFlow()

    fun setVideo(absolutePath: String) {
        _videoPath.value = absolutePath
    }

    fun uploadFile() {

    }
}
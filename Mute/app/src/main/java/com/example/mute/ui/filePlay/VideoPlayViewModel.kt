package com.example.mute.ui.filePlay

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mute.model.ContentDetailInfo
import com.example.mute.model.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoPlayViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _contentDetailInfo = MutableStateFlow(ContentDetailInfo())
    val contentDetailInfo = _contentDetailInfo.asStateFlow()

    fun getContentDetailInfo(videoId: String) {
        viewModelScope.launch {
            mainRepository.getStreamingVideo(videoId)
                .catch {
                    Log.d("mute_video_detail_info", "getContentDetailInfo error ${it.message}")
                }.collect { contentDetailInfo ->
                    _contentDetailInfo.value = contentDetailInfo
                }
        }
    }

}
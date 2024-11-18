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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddVideoViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _uploadStatus = MutableStateFlow(UploadStatus.READY)
    val uploadStatus = _uploadStatus.asStateFlow()

    private var videoPath = ""

    val mediaTitle = MutableStateFlow("")
    val mediaDescription = MutableStateFlow("")
    val performanceTitle = MutableStateFlow("")

    private val _performanceTime = MutableStateFlow("")
    val performanceTime = _performanceTime.asStateFlow()

    private val _performanceActors = MutableStateFlow("")
    val performanceActors = _performanceActors.asStateFlow()

    fun setVideo(absolutePath: String) {
        videoPath = absolutePath
    }

    fun getActors() {
        Log.e("mute_get_actors", "공연 제목 ${performanceTitle.value}")
        viewModelScope.launch {
            mainRepository.getMusicalActorList(performanceTitle.value)
                .catch {
                    Log.e("mute_get_actors", "getActors 에러 ${it.message}")
                }.collect {
                    Log.e("mute_get_actors", "getActors 결과 $it")
                }
        }
    }

    fun uploadFile() {
        _uploadStatus.value = UploadStatus.IN_PROGRESS
        val file = File(videoPath)
        val metaInfo = FileMetaInfo(
            mediaDescription.value,
            mediaTitle.value,
            performanceTitle.value,
            "",
            "1"
        )
        viewModelScope.launch {
            mainRepository.uploadVideo(file, metaInfo)
                .catch {
                    _uploadStatus.value = UploadStatus.FAILURE
                    Log.e("동영상 업로드 에러", it.toString())
                }.collectLatest { message ->
                    if (message == "fail") {
                        _uploadStatus.value = UploadStatus.FAILURE
                    } else {
                        _uploadStatus.value = UploadStatus.SUCCESS
                    }
                }
        }
    }
}
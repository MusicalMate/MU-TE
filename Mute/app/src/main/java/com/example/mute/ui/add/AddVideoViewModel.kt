package com.example.mute.ui.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mute.model.Actor
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

    private val _fileInputStatus = MutableStateFlow(FileInputStatus.EMPTY)
    val fileInputStatus = _fileInputStatus.asStateFlow()

    private val _videoPath = MutableStateFlow("")
    val videoPath = _videoPath.asStateFlow()

    val mediaTitle = MutableStateFlow("")
    val mediaDescription = MutableStateFlow("")
    val performanceTitle = MutableStateFlow("")
    val performanceTime = MutableStateFlow("")

    private val _musicalActors = MutableStateFlow<List<Actor>?>(null)
    val musicalActors = _musicalActors.asStateFlow()

    private val _selectedActor = MutableStateFlow<Actor?>(null)
    val selectedActor = _selectedActor.asStateFlow()

    fun setVideo(absolutePath: String) {
        clearAll()
        _videoPath.value = absolutePath
    }

    fun setSelectedActor(actor: Actor) {
        _selectedActor.value = actor
    }

    fun clearAll() {
        _uploadStatus.value = UploadStatus.READY
        _fileInputStatus.value = FileInputStatus.EMPTY
        _videoPath.value = ""
        mediaTitle.value = ""
        mediaDescription.value = ""
        performanceTitle.value = ""
        performanceTime.value = ""
        _musicalActors.value = null
        _selectedActor.value = null
    }

    fun getActors() {
        viewModelScope.launch {
            mainRepository.getMusicalActorList(performanceTitle.value.replace(" ", ""))
                .catch {
                    Log.e("mute_get_actors", "getActors 에러 ${it.message}")
                    _musicalActors.value = emptyList()
                }.collect { actorList ->
                    _musicalActors.value = actorList
                }
        }
    }

    fun validateAndUpload() {
        _fileInputStatus.value = checkFileInputState()
        if (fileInputStatus.value == FileInputStatus.COMPLETE) uploadFile()
    }

    private fun checkFileInputState(): FileInputStatus {
        return when {
            mediaTitle.value.isEmpty() -> FileInputStatus.FILE_TITLE_NOT_ENTERED
            performanceTitle.value.isEmpty() -> FileInputStatus.MUSICAL_TITLE_NOT_ENTERED
            !isValidTimeFormat() -> FileInputStatus.PERFORMANCE_TIME_FORMAT_ERROR
            selectedActor.value == null -> FileInputStatus.ACTOR_NOT_SELECTED
            else -> FileInputStatus.COMPLETE
        }
    }

    private fun isValidTimeFormat() : Boolean {
        val regex = Regex("^\\d{4}/\\d{2}/\\d{2}-\\d{2}:\\d{2}$")
        return regex.matches(performanceTime.value)
    }

    private fun uploadFile() {
        _uploadStatus.value = UploadStatus.IN_PROGRESS
        val file = File(videoPath.value)
        val metaInfo = FileMetaInfo(
            mediaDescription.value,
            mediaTitle.value,
            performanceTitle.value.replace(" ", ""),
            performanceTime.value.replace("-", " "),
            selectedActor.value!!.actorId
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
                        clearAll()
                    }
                }
        }
    }
}
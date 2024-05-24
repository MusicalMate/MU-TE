package com.example.mute.ui.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mute.model.dto.FileMetaRequest
import com.example.mute.model.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AddViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val mediaTitle = MutableStateFlow("")
    val mediaDescription = MutableStateFlow("")
    val hashTag = MutableStateFlow("")

    private val _performanceTitle = MutableStateFlow("")
    val performanceTitle = _performanceTitle.asStateFlow()

    private val _performanceTime = MutableStateFlow("")
    val performanceTime = _performanceTime.asStateFlow()

    private val _performanceActors = MutableStateFlow("")
    val performanceActors = _performanceActors.asStateFlow()

    fun uploadFiles() {
        viewModelScope.launch {
            mainRepository.uploadSmallFiles(
                bigFiles = emptyList(),
                smallFiles = emptyList(),
                fileMeta = FileMetaRequest(
                    fileTitle = mediaTitle.value,
                    fileDescription = mediaDescription.value,
                    musicalId = 1,
                    actorId = emptyList(),
                    fileDate = performanceTime.value,
                    hashtag = emptyList()
                )
            ).catch {
                Log.d("mute_upload_file_error", "uploadFiles error ${it.message}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val mainRepository = MainRepository()
                AddViewModel(mainRepository)
            }
        }
    }
}
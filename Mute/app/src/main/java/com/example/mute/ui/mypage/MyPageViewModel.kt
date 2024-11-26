package com.example.mute.ui.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mute.model.UserInfo
import com.example.mute.model.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class DeleteStatus {
    DELETE_READY, DELETE_FAIL, DELETE_SUCCESS
}

@HiltViewModel
class MyPageViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _userInfo = MutableStateFlow(UserInfo())
    val userInfo = _userInfo.asStateFlow()

    private val _deleteStatus = MutableStateFlow(DeleteStatus.DELETE_READY)
    val deleteStatus = _deleteStatus.asStateFlow()

    fun setDeleteStatusReady() {
        _deleteStatus.value = DeleteStatus.DELETE_READY
    }

    fun getUserInfo() {
        viewModelScope.launch {
            mainRepository.getMyPageInfo()
                .catch {
                    Log.e("mute_my_page_getUserInfo", "getUserInfo 에러 ${it.message}")
                }.collect { userInfo ->
                    _userInfo.value = userInfo
                    setDeleteStatusReady()
                }
        }
    }

    fun deleteImage(contentId: String) {
        viewModelScope.launch {
            mainRepository.deleteMyImage(contentId)
                .catch {
                    Log.e("mute_my_page_deleteImage", "deleteImage 에러 ${it.message}")
                    _deleteStatus.value = DeleteStatus.DELETE_FAIL
                }.collect {
                    _deleteStatus.value = DeleteStatus.DELETE_SUCCESS
                    getUserInfo()
                }
        }
    }

    fun deleteVideo(contentId: String) {
        viewModelScope.launch {
            mainRepository.deleteMyVideo(contentId)
                .catch {
                    Log.e("mute_my_page_deleteVideo", "deleteVideo 에러 ${it.message}")
                    _deleteStatus.value = DeleteStatus.DELETE_FAIL
                }.collect {
                    _deleteStatus.value = DeleteStatus.DELETE_SUCCESS
                    getUserInfo()
                }
        }
    }
}
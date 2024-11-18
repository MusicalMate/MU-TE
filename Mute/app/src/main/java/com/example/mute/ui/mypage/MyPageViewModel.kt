package com.example.mute.ui.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mute.model.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    fun getUserInfo() {
        viewModelScope.launch {
            mainRepository.getMyPageInfo()
                .catch {
                    Log.e("mute_my_page_getUserInfo", "getUserInfo 에러 ${it.message}")
                }.collect {
                    Log.e("mute_my_page_getUserInfo", "getUserInfo 결과 $it")
                }
        }
    }
}
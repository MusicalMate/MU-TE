package com.example.mute.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mute.model.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    fun loginWithKakao(accessToken: String) {
        viewModelScope.launch {
            loginRepository.loginWithKakao(accessToken)
                .catch {
                    Log.e("mute_login", it.message ?: "")
                }
                .collectLatest {
                    Log.e("mute_login", it.toString())
                }
        }
    }

}
package com.example.mute.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mute.model.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginResult = MutableStateFlow(false)
    val loginResult = _loginResult.asStateFlow()


    fun loginWithKakao(accessToken: String) {
        viewModelScope.launch {
            loginRepository.loginWithKakao(accessToken)
                .catch {
                    Log.e("mute_login", it.message ?: "")
                    _loginResult.value = false
                }
                .collectLatest {
                    loginRepository.saveUserInfo(accessToken, it)
                    _loginResult.value = true
                }
        }
    }

}
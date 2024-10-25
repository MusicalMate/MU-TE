package com.example.mute.model.repository

import com.example.mute.model.dto.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun loginWithKakao(accessToken: String): Flow<LoginResponse>

    suspend fun saveToken(token: String)
}
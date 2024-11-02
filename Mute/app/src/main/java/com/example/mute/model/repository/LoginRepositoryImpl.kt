package com.example.mute.model.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mute.model.LoginApi
import com.example.mute.model.dto.LoginResponse
import com.example.mute.model.dto.SignInRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val dataStore: DataStore<Preferences>
) : LoginRepository {

    override fun loginWithKakao(accessToken: String): Flow<LoginResponse> =
        flow {
            emit(loginApi.postSignInKakao(SignInRequest(accessToken)))
        }

    override suspend fun saveUserInfo(token: String, loginResponse: LoginResponse) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
            preferences[USER_ID] = loginResponse.userId?.toString() ?: ""
            preferences[USER_EMAIL] = loginResponse.email ?: ""
            preferences[USER_NICKNAME] = loginResponse.nickname ?: ""
        }
    }

    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("token")
        val USER_ID = stringPreferencesKey("userId")
        val USER_EMAIL = stringPreferencesKey("userEmail")
        val USER_NICKNAME = stringPreferencesKey("userNickname")
    }
}
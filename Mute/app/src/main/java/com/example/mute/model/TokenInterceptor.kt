package com.example.mute.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val userId = stringPreferencesKey("userId")
        val token = runBlocking {
            dataStore.data.map {
                it[userId]
            }.firstOrNull() ?: ""
        }

        val requestBuilder = chain.request().newBuilder().addHeader("userId2", token)
        return chain.proceed(requestBuilder.build())
    }
}
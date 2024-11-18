package com.example.mute.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mute.model.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    fun searchKeyword(keyword: String) {
        viewModelScope.launch {
            mainRepository.searchKeyword(keyword)
                .catch {
                    Log.e(
                        "mute_search_keyword_viewModel",
                        "mutsearchKeyworderror ${it.message}"
                    )
                }.collect {

                }
        }
    }
}
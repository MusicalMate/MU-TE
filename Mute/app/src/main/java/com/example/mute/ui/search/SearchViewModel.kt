package com.example.mute.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mute.model.SearchResult
import com.example.mute.model.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SearchStatus {
    IDLE, LOADING, SUCCESS, ERROR
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _searchResult = MutableStateFlow(SearchResult())
    val searchResult = _searchResult.asStateFlow()

    private val _searchStatus = MutableStateFlow(SearchStatus.IDLE);
    val searchStatus = _searchStatus.asStateFlow()

    fun searchKeyword(keyword: String) {
        _searchStatus.value = SearchStatus.LOADING
        viewModelScope.launch {
            mainRepository.searchKeyword(keyword)
                .catch {
                    _searchStatus.value = SearchStatus.ERROR
                }.collect { searchResult ->
                    _searchResult.value = searchResult
                    _searchStatus.value = SearchStatus.SUCCESS
                }
        }
    }
}
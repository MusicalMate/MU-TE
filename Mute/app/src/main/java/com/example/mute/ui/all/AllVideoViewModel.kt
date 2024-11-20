package com.example.mute.ui.all

import androidx.lifecycle.ViewModel
import com.example.mute.model.ContentInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AllVideoViewModel : ViewModel() {

    private val _contentListState = MutableStateFlow(ContentListState())
    val contentListState = _contentListState.asStateFlow()

    fun setViewModelState(
        contentList: List<ContentInfo>,
        star: Boolean,
        playList: Long,
        name: String
    ) {
        _contentListState.value = ContentListState(contentList, star, playList, name)
    }
}
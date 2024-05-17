package com.example.mute.ui.add

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddViewModel : ViewModel() {

    private val _mediaTitle = MutableStateFlow("")
    val mediaTitle = _mediaTitle.asStateFlow()

    private val _mediaDescription = MutableStateFlow("")
    val mediaDescription = _mediaDescription.asStateFlow()

    private val _performanceTitle = MutableStateFlow("")
    val performanceTitle = _performanceTitle.asStateFlow()

    private val _performanceTime = MutableStateFlow("")
    val performanceTime = _performanceTime.asStateFlow()

    private val _performanceActors = MutableStateFlow("")
    val performanceActors = _performanceActors.asStateFlow()

    private val _hashTag = MutableStateFlow("")
    val hashTag = _hashTag.asStateFlow()

}
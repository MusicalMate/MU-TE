package com.example.mute.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mute.MainRepository
import kotlinx.coroutines.launch

class MusicalDetailViewModel(private val repository: MainRepository): ViewModel() {

    fun getMusicalInfo(title: String) {
        viewModelScope.launch {
            val result = repository.searchMusical(title)
            Log.e("musical_검색_이름", title)
            Log.e("musical_검색_결과", result.toString())
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory{
            initializer{
                val mainRepository = MainRepository()
                MusicalDetailViewModel(mainRepository)
            }
        }
    }
}
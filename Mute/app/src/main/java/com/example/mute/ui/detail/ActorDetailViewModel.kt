package com.example.mute.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mute.MainRepository
import kotlinx.coroutines.launch

class ActorDetailViewModel(private val mainRepository: MainRepository): ViewModel() {

    fun getActorInfo(name: String){
        viewModelScope.launch {
            val result = mainRepository.searchActor(name)
            Log.e("actor_검색_이름", name)
            Log.e("actor_검색_결과", result.toString())
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory{
            initializer{
                val mainRepository = MainRepository()
                ActorDetailViewModel(mainRepository)
            }
        }
    }
}
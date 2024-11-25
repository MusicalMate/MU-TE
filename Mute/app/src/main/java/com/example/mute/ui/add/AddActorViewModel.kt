package com.example.mute.ui.add

import androidx.lifecycle.ViewModel
import com.example.mute.model.Actor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ActorSelected(
    val actorId: String,
    val actorUrl: String,
    val actorName: String,
    val isSelected: Boolean
)

class AddActorViewModel : ViewModel() {

    private val _musicalActors = MutableStateFlow<List<ActorSelected>>(emptyList())
    val musicalActors = _musicalActors.asStateFlow()

    private val _musicalTitle = MutableStateFlow("")
    val musicalTitle = _musicalTitle.asStateFlow()

    fun setMusicalActorsInfo(
        musicalActors: List<Actor>,
        actorId: String,
        musicalTitle: String
    ) {
        _musicalActors.value = musicalActors.map { actor ->
            ActorSelected(
                actorId = actor.actorId,
                actorUrl = actor.actorUrl,
                actorName = actor.actorName,
                isSelected = actor.actorId == actorId
            )
        }
        _musicalTitle.value = musicalTitle
    }

    fun selectActor(actorId: String) {
        _musicalActors.value = musicalActors.value.map { actorSelected ->
            actorSelected.copy(isSelected = actorSelected.actorId == actorId)
        }
    }

    fun getSelectedActor(): Actor? {
        return musicalActors.value.filter { it.isSelected }
            .map { Actor(actorId = it.actorId, actorUrl = it.actorUrl, actorName = it.actorName) }
            .getOrNull(0)
    }
}
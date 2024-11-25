package com.example.mute.ui.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mute.databinding.ItemActorBinding

class AddActorViewHolder(private val binding: ItemActorBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(actorSelected: ActorSelected, onClickListener: ActorClickListener) {
        binding.ivItemActor.clipToOutline = true
        binding.actorSelected = actorSelected
        binding.ivItemActor.setOnClickListener { onClickListener.onClick(actorSelected) }
    }

    companion object {
        fun from(parent: ViewGroup): AddActorViewHolder {
            return AddActorViewHolder(
                ItemActorBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}
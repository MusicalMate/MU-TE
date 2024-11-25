package com.example.mute.ui.add

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class AddActorAdapter(private val onClickListener: ActorClickListener) :
    ListAdapter<ActorSelected, AddActorViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddActorViewHolder {
        return AddActorViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AddActorViewHolder, position: Int) {
        holder.bind(currentList[position], onClickListener)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ActorSelected>() {
            override fun areContentsTheSame(
                oldItem: ActorSelected,
                newItem: ActorSelected
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: ActorSelected, newItem: ActorSelected): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}
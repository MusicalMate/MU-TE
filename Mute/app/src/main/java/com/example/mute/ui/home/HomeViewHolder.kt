package com.example.mute.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mute.databinding.ItemHomeBinding

class HomeViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(homeItem: HomeItem) {
        // TODO - binding.ivItemHome
        binding.tvItemHome.text = homeItem.name
    }

    companion object {
        fun from(parent: ViewGroup): HomeViewHolder {
            return HomeViewHolder(
                ItemHomeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}
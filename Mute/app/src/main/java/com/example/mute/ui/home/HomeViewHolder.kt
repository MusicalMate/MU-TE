package com.example.mute.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mute.databinding.ItemHomeBinding

class HomeViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(homeItem: HomeItem, onClickListener: HomeItemClickListener) {
        // TODO - binding.ivItemHome
        binding.homeItem = homeItem
        binding.ivItemHome.setOnClickListener { onClickListener.onClick(homeItem) }
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
package com.example.mute.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mute.databinding.ItemVideoBinding

class VideoViewHolder(private val binding: ItemVideoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(contentItem: ContentItem, onClickListener: ContentItemClickListener) {
        // TODO - binding.ivItemPhoto
        binding.contentItem = contentItem
        binding.layoutItemVideo.setOnClickListener { onClickListener.onClick(contentItem) }
    }

    companion object {
        fun from(parent: ViewGroup): VideoViewHolder {
            return VideoViewHolder(
                ItemVideoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

}
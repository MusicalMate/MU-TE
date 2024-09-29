package com.example.mute.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mute.databinding.ItemPhotoBinding

class PhotoViewHolder(private val binding: ItemPhotoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(contentItem: ContentItem, onClickListener: ContentItemClickListener) {
        // TODO - binding.ivItemPhoto
        binding.contentItem = contentItem
        binding.layoutItemPhoto.setOnClickListener { onClickListener.onClick(contentItem) }
    }

    companion object {
        fun from(parent: ViewGroup): PhotoViewHolder {
            return PhotoViewHolder(
                ItemPhotoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}
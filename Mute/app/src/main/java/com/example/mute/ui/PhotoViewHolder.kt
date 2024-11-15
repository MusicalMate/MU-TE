package com.example.mute.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mute.databinding.ItemPhotoBinding
import com.example.mute.model.ContentInfo

class PhotoViewHolder(private val binding: ItemPhotoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(contentInfo: ContentInfo, onClickListener: ContentItemClickListener) {
        binding.contentInfo = contentInfo
        binding.layoutItemPhoto.setOnClickListener { onClickListener.onClick(contentInfo) }
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
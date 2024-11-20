package com.example.mute.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mute.databinding.ItemDetailContentBinding
import com.example.mute.model.ContentInfo

class DetailViewHolder(private val binding: ItemDetailContentBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(contentInfo: ContentInfo, onClickListener: ContentItemClickListener) {
        binding.contentInfo = contentInfo
        binding.layoutItemDetailContent.setOnClickListener { onClickListener.onClick(contentInfo) }
    }

    companion object {
        fun from(parent: ViewGroup): DetailViewHolder {
            return DetailViewHolder(
                ItemDetailContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}
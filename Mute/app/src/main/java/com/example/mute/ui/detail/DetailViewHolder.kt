package com.example.mute.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mute.databinding.ItemDetailContentBinding
import com.example.mute.model.ContentInfo
import com.example.mute.ui.ContentItemClickListener

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
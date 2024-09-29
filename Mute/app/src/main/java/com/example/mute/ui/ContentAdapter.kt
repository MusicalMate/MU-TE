package com.example.mute.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ContentAdapter(
    private val contentType: ContentType,
    private val onClickListener: ContentItemClickListener
) :
    ListAdapter<ContentItem, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (contentType) {
            ContentType.PHOTO -> PhotoViewHolder.from(parent)
            ContentType.VIDEO -> VideoViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhotoViewHolder -> holder.bind(currentList[position], onClickListener)
            is VideoViewHolder -> holder.bind(currentList[position], onClickListener)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ContentItem>() {
            override fun areContentsTheSame(oldItem: ContentItem, newItem: ContentItem): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: ContentItem, newItem: ContentItem): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}
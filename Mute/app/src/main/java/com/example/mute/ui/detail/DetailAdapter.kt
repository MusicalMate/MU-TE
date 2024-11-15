package com.example.mute.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mute.model.ContentInfo
import com.example.mute.ui.ContentItemClickListener

class DetailAdapter(private val onClickListener: ContentItemClickListener) :
    ListAdapter<ContentInfo, DetailViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(currentList[position], onClickListener)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ContentInfo>() {
            override fun areContentsTheSame(oldItem: ContentInfo, newItem: ContentInfo): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: ContentInfo, newItem: ContentInfo): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}
package com.example.mute.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class HomeAdapter(private val onClickListener: HomeItemClickListener) :
    ListAdapter<HomeItem, HomeViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(currentList[position], onClickListener)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<HomeItem>() {
            override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}
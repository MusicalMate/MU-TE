package com.example.mute.ui.mypage

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mute.model.ContentInfo
import com.example.mute.ui.ContentItemClickListener

class MyPageAdapter(
    private val clickListener: ContentItemClickListener,
    private val popUpClickListener: PopUpClickListener
) : ListAdapter<ContentInfo, MyPageViewHolder>(diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPageViewHolder {
        return MyPageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyPageViewHolder, position: Int) {
        holder.onBind(currentList[position], clickListener, popUpClickListener)
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
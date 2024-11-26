package com.example.mute.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.mute.R
import com.example.mute.databinding.ItemMyPageFileBinding
import com.example.mute.model.ContentInfo
import com.example.mute.ui.ContentItemClickListener

class MyPageViewHolder(private val binding: ItemMyPageFileBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(
        contentInfo: ContentInfo,
        clickListener: ContentItemClickListener,
        popUpClickListener: PopUpClickListener
    ) {
        binding.contentInfo = contentInfo

        binding.ivItemVideo.setOnClickListener {
            clickListener.onClick(contentInfo)
        }

        binding.ivMyPageFileMore.setOnClickListener {
            val popup = PopupMenu(binding.ivMyPageFileMore.context, binding.ivMyPageFileMore)
            popup.inflate(R.menu.my_file_menu)
            popup.setOnMenuItemClickListener { menu ->
                when (menu.itemId) {
                    R.id.my_file_delete -> popUpClickListener.delete(contentInfo.contentId)
                    else -> {}
                }
                true
            }
            popup.show()
        }
    }

    companion object {
        fun from(parent: ViewGroup): MyPageViewHolder {
            return MyPageViewHolder(
                ItemMyPageFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}
package com.example.mute

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HomeAdapter(private val homeItems: List<HomeItem>) : RecyclerView.Adapter<HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(homeItems[position])
    }

    override fun getItemCount(): Int {
        return homeItems.size
    }
}
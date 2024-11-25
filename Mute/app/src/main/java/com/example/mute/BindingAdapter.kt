package com.example.mute

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.mute.model.Actor
import com.example.mute.ui.search.SearchStatus

@BindingAdapter("filmos")
fun TextView.setFilmos(filmos: List<String>) {
    text = filmos.joinToString(", ")
}

@BindingAdapter("actors")
fun TextView.setActors(actors: List<Actor>) {
    text = actors.joinToString(", ") { it.actorName }
}

@BindingAdapter("ActorMusicalImg")
fun ImageView.setImage(url: String?) {
    Glide.with(this)
        .load(url)
        .into(this)
}

@BindingAdapter("AddFileImg")
fun ImageView.setFileImage(uri: String?) {
    Glide.with(this).load(uri).fitCenter().into(this)
}

@BindingAdapter("SearchResultVisibility")
fun View.setSearchResultVisibility(status: SearchStatus) {
    visibility = when(status) {
        SearchStatus.SUCCESS -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("ListToString")
fun TextView.setListToString(strings: List<String>){
    text = strings.joinToString(", ")
}
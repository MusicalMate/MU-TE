package com.example.mute

import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("filmos")
fun TextView.setFilmos(filmos: List<Filmography>) {
    text = filmos.joinToString(", ") { it.filmographyTitle }
}

@BindingAdapter("actors")
fun TextView.setActors(actors: List<Actor>) {
    text = actors.joinToString(", ") { it.name }
}

@BindingAdapter("ActorMusicalImg")
fun ImageView.setImage(url: String?) {
    Glide.with(this)
        .load(Base64.decode(url, Base64.DEFAULT))
        .into(this)
}

@BindingAdapter("AddFileImg")
fun ImageView.setFileImage(uri: String?) {
    Glide.with(this).load(uri).fitCenter().into(this)
}
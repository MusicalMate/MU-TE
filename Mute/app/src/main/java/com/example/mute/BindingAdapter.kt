package com.example.mute

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("filmos")
fun TextView.setFilmos(filmos: List<Filmography>) {
    text = filmos.joinToString(", ") { it.filmographyTitle }
}

@BindingAdapter("actors")
fun TextView.setActors(actors: List<Actor>) {
    text = actors.joinToString(", ") { it.name }
}
package com.example.paymenmethodexam.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.paymenmethodexam.R

fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_placeholder_image)
        .error(R.drawable.ic_broken_image)
        .into(this)
}

fun ImageView.loadGif(url: String) {
    Glide.with(this)
        .asGif()
        .load(url)
        .placeholder(R.drawable.ic_placeholder_image)
        .error(R.drawable.ic_broken_image)
        .into(this)
}
package com.example.paymenmethodexam.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.paymenmethodexam.R
import com.google.android.material.snackbar.Snackbar

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

fun ImageView.loadUrl(url: String) {
    with(url) {
        when {
            contains(".gif") -> loadGif(url)
            else -> load(url)
        }
    }
}

fun View.snackBar(message: String, messageButton: String = "OK", action: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackBar.setAction(messageButton) {
            it()
        }
    }
    snackBar.show()
}
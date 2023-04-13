package com.example.paymenmethodexam.utils

import android.text.InputFilter
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.paymenmethodexam.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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

fun Fragment.validateFields(vararg textFields: TextInputLayout): Boolean {
    var isValid = true

    for (textField in textFields) {
        if (textField.editText?.text.toString().trim().isEmpty()) {
            textField.error = getString(R.string.helper_required)
            isValid = false
        } else textField.error = null
    }
    return isValid
}

fun TextInputEditText.toUpperCase(valid: Boolean = false) {
    if (valid) filters = arrayOf<InputFilter>(InputFilter.AllCaps())
}
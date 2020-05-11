package com.footballapp.ext

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(text: String, duration: Int = Snackbar.LENGTH_LONG) =
    Snackbar.make(this, text, duration).show()

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun ImageView.loadImage(activity: Activity, uri: String) {
    GlideToVectorYou.justLoadImage(activity, Uri.parse(uri), this)
    Glide.with(context)
        .load(uri)
        .into(this)
}

fun View.setVisible() {
    this.visibility = View.VISIBLE
}
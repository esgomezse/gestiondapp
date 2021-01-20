package com.umb.gestiondapp

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


/**
 * Created By Juan Felipe Arango on 19/01/21
 * Copyright ©2020 Merqueo. All rights reserved
 */

fun TextView.boldTitle(title: String?, text: String?) {
    if (text == null || title == null) return
    val builder = SpannableStringBuilder(title)
    builder.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    builder.append(" $text")
    this.text = builder
}

fun ImageView.setImageUrl(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_image_not_supported)
        .into(this)
}
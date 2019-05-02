package com.puboe.kotlin.githubrepos.core

import android.view.View
import android.widget.TextView

fun TextView.setTextOrHide(text: String?) {
    visibility = when (text.isNullOrEmpty()) {
        true -> View.GONE
        false -> {
            this.text = text
            View.VISIBLE
        }
    }
}
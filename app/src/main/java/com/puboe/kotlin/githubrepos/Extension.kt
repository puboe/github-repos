package com.puboe.kotlin.githubrepos

import android.view.View
import android.widget.TextView
import com.puboe.kotlin.githubrepos.repositories.entities.Repository
import org.json.JSONArray
import org.json.JSONObject

fun TextView.setTextOrHide(text: String?) {
    visibility = when (text.isNullOrEmpty()) {
        true -> View.GONE
        false -> {
            this.text = text
            View.VISIBLE
        }
    }
}
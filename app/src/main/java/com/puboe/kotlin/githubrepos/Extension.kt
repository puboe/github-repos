package com.puboe.kotlin.githubrepos

import android.view.View
import android.widget.TextView
import com.puboe.kotlin.githubrepos.domain.Repository
import org.json.JSONArray
import org.json.JSONObject

fun JSONArray.toRepositoryList(): List<Repository> {
    val repoList = ArrayList<Repository>(length())
    for (i in 0 until length()) {
        val jsonObject = get(i) as JSONObject
        val description = jsonObject.optString("description")
        repoList.add(
            Repository(
                jsonObject.optLong("id"),
                jsonObject.optString("name"),
                if (description == "null") null else description    // Handle weird json parsing.
            )
        )
    }
    return repoList
}

fun TextView.setTextOrHide(text: String?) {
    visibility = when (text.isNullOrEmpty()) {
        true -> View.GONE
        false -> {
            this.text = text
            View.VISIBLE
        }
    }
}
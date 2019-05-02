package com.puboe.kotlin.githubrepos.repositories.network

import com.puboe.kotlin.githubrepos.core.provider.DataMapper
import com.puboe.kotlin.githubrepos.repositories.entities.Repository
import org.json.JSONArray
import org.json.JSONObject

class RepositoriesMapper : DataMapper<JSONArray, List<Repository>> {

    override fun map(source: JSONArray): List<Repository> {
        val repoList = ArrayList<Repository>(source.length())
        for (i in 0 until source.length()) {
            val jsonObject = source.get(i) as JSONObject
            val description = jsonObject.optString("description")
            repoList.add(
                Repository(
                    jsonObject.optLong("id"),
                    jsonObject.optJSONObject("owner").optString("login"),
                    jsonObject.optString("name"),
                    if (description == "null") null else description    // Handle weird json parsing.
                )
            )
        }
        return repoList
    }
}
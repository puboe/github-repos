package com.puboe.kotlin.githubrepos.repositories.network

import com.puboe.kotlin.githubrepos.core.provider.DataMapper
import com.puboe.kotlin.githubrepos.repositories.entities.Commit
import org.json.JSONArray
import org.json.JSONObject

class CommitsMapper : DataMapper<Pair<String, JSONArray>, Commit> {

    override fun map(source: Pair<String, JSONArray>): Commit {
        // Get last commit
        val jsonObject = source.second.get(0) as JSONObject
        val commit = jsonObject.optJSONObject("commit")
        return Commit(
            source.first,
            commit?.optJSONObject("author")?.optString("name"),
            commit?.optString("message")
        )
    }
}
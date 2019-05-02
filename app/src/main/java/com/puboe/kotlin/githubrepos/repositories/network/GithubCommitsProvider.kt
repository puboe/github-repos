package com.puboe.kotlin.githubrepos.repositories.network

import com.puboe.kotlin.githubrepos.core.provider.DataMapper
import com.puboe.kotlin.githubrepos.core.provider.DataProvider
import com.puboe.kotlin.githubrepos.repositories.entities.Commit
import com.puboe.kotlin.githubrepos.repositories.entities.CommitState
import org.json.JSONArray

class GithubCommitsProvider(
    private val mapper: DataMapper<Pair<String, JSONArray>, Commit>
) : BaseDataProvider(), DataProvider<GithubCommitsProvider.CommitParams, CommitState> {

    override fun requestData(params: CommitParams, callback: (CommitState) -> Unit) {
        callback(CommitState.Loading)
        try {
            val response = doGet("${BASE_URL}repos/${params.username}/${params.repository}/commits")
            callback(CommitState.Success(mapper.map(Pair(params.repository, JSONArray(response)))))
        } catch (e: Exception) {
            callback(CommitState.Error)
        }
    }

    data class CommitParams(val username: String, val repository: String)
}
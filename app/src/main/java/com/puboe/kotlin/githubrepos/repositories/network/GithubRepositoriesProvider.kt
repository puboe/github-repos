package com.puboe.kotlin.githubrepos.repositories.network

import com.puboe.kotlin.githubrepos.core.provider.DataMapper
import com.puboe.kotlin.githubrepos.core.provider.DataProvider
import com.puboe.kotlin.githubrepos.repositories.entities.RepositoriesState
import com.puboe.kotlin.githubrepos.repositories.entities.Repository
import org.json.JSONArray

class GithubRepositoriesProvider(
    private val mapper: DataMapper<JSONArray, List<Repository>>
) : BaseDataProvider(), DataProvider<String, RepositoriesState> {

    override fun requestData(params: String, callback: (RepositoriesState) -> Unit) {
        callback(RepositoriesState.Loading)
        try {
            val response = doGet("${BASE_URL}users/$params/repos")
            callback(RepositoriesState.Success(mapper.map(JSONArray(response))))
        } catch (e: Exception) {
            callback(RepositoriesState.Error)
        }
    }
}
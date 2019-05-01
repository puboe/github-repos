package com.puboe.kotlin.githubrepos.repositories.network

import com.puboe.kotlin.githubrepos.core.provider.DataProvider
import com.puboe.kotlin.githubrepos.data.GithubRepository
import com.puboe.kotlin.githubrepos.repositories.entities.RepositoriesState

/**
 * Network [GithubRepository] implementation.
 */
class GithubRepositoryImpl(
    private val repositoriesProvider: DataProvider<String, RepositoriesState>
) : GithubRepository {

    override fun getRepositories(username: String, callback: (RepositoriesState) -> Unit) {
        repositoriesProvider.requestData(username, callback)
    }

//    override fun getCommits(username: String, repository: String): RepositoriesState {
//        try {
//            val response = doGet("${BASE_URL}users/$username/$repository/commits")
//            return RepositoriesState.Success(RepositoriesMapper().map(JSONArray(response)))
//        } catch (e: Exception) {
//        }
//        return RepositoriesState.Error
//    }
}
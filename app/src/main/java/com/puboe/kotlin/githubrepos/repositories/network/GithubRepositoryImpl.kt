package com.puboe.kotlin.githubrepos.repositories.network

import com.puboe.kotlin.githubrepos.core.provider.DataProvider
import com.puboe.kotlin.githubrepos.repositories.entities.GithubRepository
import com.puboe.kotlin.githubrepos.repositories.entities.CommitState
import com.puboe.kotlin.githubrepos.repositories.entities.RepositoriesState

/**
 * Network [GithubRepository] implementation.
 */
class GithubRepositoryImpl(
    private val repositoriesProvider: DataProvider<String, RepositoriesState>,
    private val commitsProvider: DataProvider<GithubCommitsProvider.CommitParams, CommitState>
) : GithubRepository {

    override fun getRepositories(username: String, callback: (RepositoriesState) -> Unit) {
        // TODO: Implement cache logic.
        repositoriesProvider.requestData(username, callback)
    }

    override fun getCommits(username: String, repository: String, callback: (CommitState) -> Unit) {
        commitsProvider.requestData(GithubCommitsProvider.CommitParams(username, repository), callback)
    }
}
package com.puboe.kotlin.githubrepos.data

import com.puboe.kotlin.githubrepos.repositories.entities.CommitState
import com.puboe.kotlin.githubrepos.repositories.entities.RepositoriesState

interface GithubRepository {

    /**
     * Get the list of repositories for the given user.
     */
    fun getRepositories(username: String, callback: (RepositoriesState) -> Unit)

    /**
     * Get commit for the given repository.
     */
    fun getCommits(username: String, repository: String, callback: (CommitState) -> Unit)
}

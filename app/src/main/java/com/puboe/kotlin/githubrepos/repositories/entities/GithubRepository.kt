package com.puboe.kotlin.githubrepos.repositories.entities

interface GithubRepository {

    /**
     * Get the list of repositories for the given user.
     */
    suspend fun getRepositories(username: String, callback: (RepositoriesState) -> Unit)

    /**
     * Get commit for the given repository.
     */
    suspend fun getCommits(username: String, repository: String, callback: (CommitState) -> Unit)
}

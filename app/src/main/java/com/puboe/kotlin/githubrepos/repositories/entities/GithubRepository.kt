package com.puboe.kotlin.githubrepos.repositories.entities

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

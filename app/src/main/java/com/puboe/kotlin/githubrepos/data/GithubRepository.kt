package com.puboe.kotlin.githubrepos.data

import com.puboe.kotlin.githubrepos.domain.Result

interface GithubRepository {

    /**
     * Get the list of repositories for the given user.
     **/
    fun getRepositories(username: String): Result
}
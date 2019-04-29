package com.puboe.kotlin.githubrepos.interactor

import com.puboe.kotlin.githubrepos.data.GithubRepository
import com.puboe.kotlin.githubrepos.domain.Result
import kotlinx.coroutines.*

class GetRepositories(private val githubRepository: GithubRepository) {

    suspend fun run(username: String) = githubRepository.getRepositories(username)

    operator fun invoke(username: String, onResult: (Result) -> Unit = {}) {
        val job = CoroutineScope(Dispatchers.Default).async { run(username) }
        MainScope().launch { onResult(job.await()) }
    }
}
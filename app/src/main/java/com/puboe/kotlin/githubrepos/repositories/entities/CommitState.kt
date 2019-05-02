package com.puboe.kotlin.githubrepos.repositories.entities

sealed class CommitState {

    class Success(val commits: Commit) : CommitState()

    object Error : CommitState()

    object Loading : CommitState()
}
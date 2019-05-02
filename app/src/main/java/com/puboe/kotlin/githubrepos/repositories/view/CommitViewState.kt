package com.puboe.kotlin.githubrepos.repositories.view

import com.puboe.kotlin.githubrepos.repositories.entities.Commit

sealed class CommitViewState {

    class ShowCommit(val commit: Commit) : CommitViewState()

    object ShowError : CommitViewState()

    object ShowLoading : CommitViewState()
}
package com.puboe.kotlin.githubrepos.repositories.view

import com.puboe.kotlin.githubrepos.repositories.entities.Repository

sealed class RepositoriesViewState {

    class ShowRepositories(val repositories: List<Repository>) : RepositoriesViewState()

    object ShowError : RepositoriesViewState()

    object ShowLoading : RepositoriesViewState()
}
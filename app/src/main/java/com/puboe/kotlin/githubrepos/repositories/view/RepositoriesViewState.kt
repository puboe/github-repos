package com.puboe.kotlin.githubrepos.repositories.view

import com.puboe.kotlin.githubrepos.repositories.entities.Repository

sealed class RepositoriesViewState {

    object Loading : RepositoriesViewState()
    class ShowRepositories(val repositories: List<Repository>) : RepositoriesViewState()
    object ShowError : RepositoriesViewState()
}
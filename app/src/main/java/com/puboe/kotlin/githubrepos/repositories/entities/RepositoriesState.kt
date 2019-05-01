package com.puboe.kotlin.githubrepos.repositories.entities

sealed class RepositoriesState {

    object Loading : RepositoriesState()

    class Success(val repositories: List<Repository>) : RepositoriesState()

    object Error : RepositoriesState()
}
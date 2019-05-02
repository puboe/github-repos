package com.puboe.kotlin.githubrepos.repositories.entities

sealed class RepositoriesState {

    class Success(val repositories: List<Repository>) : RepositoriesState()

    object Error : RepositoriesState()

    object Loading : RepositoriesState()
}
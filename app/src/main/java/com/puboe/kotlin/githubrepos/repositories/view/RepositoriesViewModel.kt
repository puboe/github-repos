package com.puboe.kotlin.githubrepos.repositories.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.puboe.kotlin.githubrepos.repositories.entities.CommitState
import com.puboe.kotlin.githubrepos.repositories.entities.GithubRepository
import com.puboe.kotlin.githubrepos.repositories.entities.RepositoriesState
import com.puboe.kotlin.githubrepos.repositories.entities.Repository
import kotlinx.coroutines.launch

class RepositoriesViewModel(private val repository: GithubRepository) : ViewModel() {

    private val repositoryLiveData: MutableLiveData<RepositoriesViewState> = MutableLiveData()
    private val commitsLiveData: MutableLiveData<CommitViewState> = MutableLiveData()

    val repositoriesViewState: LiveData<RepositoriesViewState>
        get() = repositoryLiveData

    val commitsViewState: LiveData<CommitViewState>
        get() = commitsLiveData

    fun getRepositories(username: String) {
        viewModelScope.launch {
            // Get repositories with main-safe suspend function.
            repository.getRepositories(username) { state ->
                updateRepositoriesState(state)
            }
        }
    }

    private fun getCommits(username: String, repositoryName: String) {
        viewModelScope.launch {
            // Get commits with main-safe suspend function.
            repository.getCommits(username, repositoryName) { state ->
                updateCommitState(state)
            }
        }
    }

    private fun updateRepositoriesState(state: RepositoriesState) {
        repositoryLiveData.value = when (state) {
            is RepositoriesState.Loading -> RepositoriesViewState.ShowLoading
            is RepositoriesState.Error -> RepositoriesViewState.ShowError
            is RepositoriesState.Success -> {
                requestCommits(state.repositories)
                RepositoriesViewState.ShowRepositories(state.repositories)
            }
        }
    }

    private fun requestCommits(repositories: List<Repository>) {
        repositories.forEach {
            getCommits(it.username, it.name)
        }
    }

    private fun updateCommitState(state: CommitState) {
        commitsLiveData.value = when (state) {
            is CommitState.Success -> CommitViewState.ShowCommit(state.commits)
            is CommitState.Loading -> CommitViewState.ShowLoading
            is CommitState.Error -> CommitViewState.ShowError
        }
    }

    class Factory(val repository: GithubRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RepositoriesViewModel(repository) as T
        }
    }
}
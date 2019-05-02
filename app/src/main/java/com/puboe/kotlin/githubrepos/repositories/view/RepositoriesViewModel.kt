package com.puboe.kotlin.githubrepos.repositories.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.puboe.kotlin.githubrepos.repositories.entities.GithubRepository
import com.puboe.kotlin.githubrepos.repositories.entities.CommitState
import com.puboe.kotlin.githubrepos.repositories.entities.RepositoriesState
import com.puboe.kotlin.githubrepos.repositories.entities.Repository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RepositoriesViewModel(private val repository: GithubRepository) : ViewModel(), CoroutineScope {

    private val repositoryLiveData: MutableLiveData<RepositoriesViewState> = MutableLiveData()
    private val commitsLiveData: MutableLiveData<CommitViewState> = MutableLiveData()

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val repositoriesViewState: LiveData<RepositoriesViewState>
        get() = repositoryLiveData

    val commitsViewState: LiveData<CommitViewState>
        get() = commitsLiveData

    fun getRepositories(username: String) {
        launch {
            // Get repositories in background thread.
            withContext(Dispatchers.IO) {
                repository.getRepositories(username) { state ->
                    // Update state in main thread.
                    updateRepositoriesState(state)
                }
            }
        }
    }

    private fun getCommits(username: String, repositoryName: String) {
        launch {
            // Get commits in background thread.
            withContext(Dispatchers.IO) {
                repository.getCommits(username, repositoryName) { state ->
                    // Update state in main thread.
                    updateCommitState(state)
                }
            }
        }
    }

    private fun updateRepositoriesState(state: RepositoriesState) {
        launch {
            withContext(Dispatchers.Main) {
                repositoryLiveData.value = when (state) {
                    is RepositoriesState.Loading -> RepositoriesViewState.ShowLoading
                    is RepositoriesState.Error -> RepositoriesViewState.ShowError
                    is RepositoriesState.Success -> {
                        requestCommits(state.repositories)
                        RepositoriesViewState.ShowRepositories(state.repositories)
                    }
                }
            }
        }
    }

    private fun requestCommits(repositories: List<Repository>) {
        repositories.forEach {
            getCommits(it.username, it.name)
        }
    }

    private fun updateCommitState(state: CommitState) {
        launch {
            withContext(Dispatchers.Main) {
                commitsLiveData.value = when (state) {
                    is CommitState.Success -> CommitViewState.ShowCommit(state.commits)
                    is CommitState.Loading -> CommitViewState.ShowLoading
                    is CommitState.Error -> CommitViewState.ShowError
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    class Factory(val repository: GithubRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RepositoriesViewModel(repository) as T
        }
    }
}
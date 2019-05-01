package com.puboe.kotlin.githubrepos.repositories.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.puboe.kotlin.githubrepos.data.GithubRepository
import com.puboe.kotlin.githubrepos.repositories.entities.RepositoriesState
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RepositoriesViewModel(private val repository: GithubRepository) : ViewModel(), CoroutineScope {

    private val mutableLiveData: MutableLiveData<RepositoriesViewState> = MutableLiveData()

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val repositoriesViewState: LiveData<RepositoriesViewState>
        get() = mutableLiveData

    fun getRepositories(username: String) {
        launch {
            // Get repositories in background thread.
            withContext(Dispatchers.IO) {
                repository.getRepositories(username) { state ->
                    // Update state in main thread.
                    update(state)
                }
            }
        }
//        interactor(username) {
//            when (it) {
//                is Success<*> -> handleSuccess(it.result as List<Repository>)
//                is Failure -> handleFailure(it)
//            }
//        }
    }

    private fun update(state: RepositoriesState) {
        launch {
            withContext(Dispatchers.Main) {
                mutableLiveData.value = when (state) {
                    is RepositoriesState.Loading -> RepositoriesViewState.Loading
                    is RepositoriesState.Success -> RepositoriesViewState.ShowRepositories(state.repositories)
                    is RepositoriesState.Error -> RepositoriesViewState.ShowError
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

//    private fun handleSuccess(result: List<Repository>) {
//        mutableLiveData.value = result
//    }

//    private fun handleFailure(result: Failure) {
//        failureLiveData.value = result
//    }

    class Factory(val repository: GithubRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RepositoriesViewModel(repository) as T
        }
    }
}
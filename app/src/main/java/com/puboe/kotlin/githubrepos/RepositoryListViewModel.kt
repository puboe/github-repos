package com.puboe.kotlin.githubrepos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.puboe.kotlin.githubrepos.domain.Failure
import com.puboe.kotlin.githubrepos.domain.Repository
import com.puboe.kotlin.githubrepos.domain.Success
import com.puboe.kotlin.githubrepos.interactor.GetRepositories

class RepositoryListViewModel(private val interactor: GetRepositories) : ViewModel() {

    val successLiveData: MutableLiveData<List<Repository>> = MutableLiveData()
    val failureLiveData: MutableLiveData<Failure> = MutableLiveData()

    fun getRepositories(username: String) {
        interactor(username) {
            when (it) {
                is Success<*> -> handleSuccess(it.result as List<Repository>)
                is Failure -> handleFailure(it)
            }
        }
    }

    private fun handleSuccess(result: List<Repository>) {
        successLiveData.value = result
    }

    private fun handleFailure(result: Failure) {
        failureLiveData.value = result
    }

    class Factory(val interactor: GetRepositories) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RepositoryListViewModel(interactor) as T
        }
    }
}
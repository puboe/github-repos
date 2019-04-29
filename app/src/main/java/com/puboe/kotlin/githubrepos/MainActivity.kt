package com.puboe.kotlin.githubrepos

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.puboe.kotlin.githubrepos.data.network.NetworkGithubRepository
import com.puboe.kotlin.githubrepos.domain.Failure
import com.puboe.kotlin.githubrepos.domain.Repository
import com.puboe.kotlin.githubrepos.interactor.GetRepositories
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private val repositoryListViewModel: RepositoryListViewModel by lazy {
        ViewModelProviders.of(
            this,
            RepositoryListViewModel.Factory(GetRepositories(NetworkGithubRepository()))
        ).get(RepositoryListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository_list.layoutManager = LinearLayoutManager(this)
        repository_list.adapter = RepoListAdapter(emptyList())

        val successObserver = Observer<List<Repository>> { repositories ->
            hideLoading()
            updateResults(repositories)
        }

        val failureObserver = Observer<Failure> {
            hideLoading()
            showError()
        }

        repositoryListViewModel.successLiveData.observe(this, successObserver)
        repositoryListViewModel.failureLiveData.observe(this, failureObserver)
    }

    override fun onStart() {
        super.onStart()

        getRepositories()
    }

    private fun getRepositories() {
        showLoading()
        repositoryListViewModel.getRepositories("puboe")
    }

    private fun updateResults(results: List<Repository>) {
        (repository_list.adapter as RepoListAdapter).updateItems(results)
    }

}
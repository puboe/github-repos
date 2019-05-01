package com.puboe.kotlin.githubrepos.repositories.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.puboe.kotlin.githubrepos.R
import com.puboe.kotlin.githubrepos.core.view.BaseActivity
import com.puboe.kotlin.githubrepos.repositories.entities.Repository
import com.puboe.kotlin.githubrepos.repositories.network.GithubRepositoriesProvider
import com.puboe.kotlin.githubrepos.repositories.network.GithubRepositoryImpl
import com.puboe.kotlin.githubrepos.repositories.network.RepositoriesMapper
import kotlinx.android.synthetic.main.activity_repositories.*

class RepositoriesActivity : BaseActivity() {

    private val repositoriesViewModel: RepositoriesViewModel by lazy {
        ViewModelProviders.of(
            this,
            RepositoriesViewModel.Factory(
                GithubRepositoryImpl(GithubRepositoriesProvider(RepositoriesMapper()))
            )
        ).get(RepositoriesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositories)

        setupView()
    }

    override fun onResume() {
        super.onResume()
        repositoriesViewModel.repositoriesViewState
            .observe(this, Observer { newState -> viewStateChanged(newState) })
    }

    private fun setupView() {
        repository_list.apply {
            layoutManager = LinearLayoutManager(this@RepositoriesActivity)
            adapter = RepositoriesAdapter(emptyList())
        }

        search_button.setOnClickListener {
            val username = username_input.text?.toString()
            if (!username.isNullOrBlank()) {
                getRepositories(username)
            }
        }
    }

    private fun viewStateChanged(state: RepositoriesViewState) {
        when (state) {
            is RepositoriesViewState.Loading -> showLoading()
            is RepositoriesViewState.ShowError -> showError()
            is RepositoriesViewState.ShowRepositories -> updateResults(state.repositories)
        }
    }

    private fun getRepositories(username: String) {
        repositoriesViewModel.getRepositories(username)
    }

    private fun updateResults(results: List<Repository>) {
        hideLoading()
        (repository_list.adapter as RepositoriesAdapter).updateItems(results)
    }
}
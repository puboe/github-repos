package com.puboe.kotlin.githubrepos.repositories.view

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.puboe.kotlin.githubrepos.R
import com.puboe.kotlin.githubrepos.core.view.BaseActivity
import com.puboe.kotlin.githubrepos.repositories.entities.Commit
import com.puboe.kotlin.githubrepos.repositories.entities.Repository
import com.puboe.kotlin.githubrepos.repositories.network.*
import kotlinx.android.synthetic.main.activity_repositories.*

class RepositoriesActivity : BaseActivity() {

    // Could be improved with DI framework such as Dagger2.
    private val repositoriesViewModel: RepositoriesViewModel by lazy {
        ViewModelProviders.of(
            this,
            RepositoriesViewModel.Factory(
                GithubRepositoryImpl(
                    GithubRepositoriesProvider(RepositoriesMapper()),
                    GithubCommitsProvider(CommitsMapper())
                )
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
            .observe(this, Observer { newState -> repositoriesStateChanged(newState) })
        repositoriesViewModel.commitsViewState
            .observe(this, Observer { newState -> commitStateChanged(newState) })
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
            hideKeyboard()
        }
    }

    private fun repositoriesStateChanged(state: RepositoriesViewState) {
        when (state) {
            is RepositoriesViewState.ShowLoading -> showLoading()
            is RepositoriesViewState.ShowError -> showError()
            is RepositoriesViewState.ShowRepositories -> updateResults(state.repositories)
        }
    }

    private fun commitStateChanged(state: CommitViewState) {
        when (state) {
            is CommitViewState.ShowCommit -> updateRepositoryCommit(state.commit)
            // Do nothing for loading and error states at this point.
            // We might want to add a progress bar or show some kind of error in the future.
            is CommitViewState.ShowLoading -> {}
            is CommitViewState.ShowError -> {}
        }
    }

    private fun updateRepositoryCommit(commit: Commit) {
        (repository_list.adapter as RepositoriesAdapter).updateRepositoryCommit(commit)
    }

    private fun getRepositories(username: String) {
        repositoriesViewModel.getRepositories(username)
    }

    private fun updateResults(results: List<Repository>) {
        hideLoading()
        (repository_list.adapter as RepositoriesAdapter).updateItems(results)
    }

    private fun hideKeyboard() {
        currentFocus?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}
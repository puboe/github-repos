package com.puboe.kotlin.githubrepos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puboe.kotlin.githubrepos.domain.Repository

class RepoListAdapter(var repos: List<Repository>) : RecyclerView.Adapter<RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder =
        RepositoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.repository_row, parent, false))


    override fun getItemCount(): Int = repos.size


    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) = holder.bind(repos[position])

    fun updateItems(updatedRepos: List<Repository>) {
        repos = updatedRepos
        notifyDataSetChanged()
    }
}
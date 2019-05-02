package com.puboe.kotlin.githubrepos.repositories.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.puboe.kotlin.githubrepos.R
import com.puboe.kotlin.githubrepos.repositories.entities.Repository
import com.puboe.kotlin.githubrepos.core.setTextOrHide
import kotlinx.android.synthetic.main.repository_row.view.*

class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(repo: Repository) {
        itemView.repo_name.setTextOrHide(repo.name)
        itemView.repo_description.setTextOrHide(repo.description)

        if (repo.lastCommit == null) {
            itemView.last_commit_info.visibility = View.GONE
        } else {
            itemView.last_commit_info.setTextOrHide(
                itemView.resources.getString(
                    R.string.last_commit_info,
                    repo.lastCommit?.author,
                    repo.lastCommit?.message
                )
            )
        }
    }
}

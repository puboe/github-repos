package com.puboe.kotlin.githubrepos

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.puboe.kotlin.githubrepos.domain.Repository
import kotlinx.android.synthetic.main.repository_row.view.*

class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(repo: Repository) {
        itemView.repo_name.setTextOrHide(repo.name)
        itemView.repo_description.setTextOrHide(repo.description)
    }
}
package com.puboe.kotlin.githubrepos.repositories.entities

data class Repository(
    val id: Long,
    val username: String,
    val name: String,
    val description: String?,
    var lastCommit: Commit? = null
)
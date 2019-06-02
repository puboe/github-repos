package com.puboe.kotlin.githubrepos.core.provider

interface DataProvider<in P, out T> {

    suspend fun requestData(params: P, callback: (T) -> Unit)
}

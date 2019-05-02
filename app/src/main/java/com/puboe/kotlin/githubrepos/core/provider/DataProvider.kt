package com.puboe.kotlin.githubrepos.core.provider

interface DataProvider<in P, out T> {

    fun requestData(params: P, callback: (T) -> Unit)
}

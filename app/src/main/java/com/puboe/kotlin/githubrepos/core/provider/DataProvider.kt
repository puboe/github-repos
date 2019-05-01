package com.puboe.kotlin.githubrepos.core.provider

interface DataProvider<P, T> {

    fun requestData(params: P, callback: (T) -> Unit)
}
